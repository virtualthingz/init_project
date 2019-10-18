package kr.supergate.shoppingfolder.domain;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.png.PngDirectory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.supergate.shoppingfolder.exception.BadRequestException;
import kr.supergate.shoppingfolder.util.ImageValidator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class WithImage {

    public static String IMAGE_VERSION_FIRST = "1";

    @Setter
    private String imageUrl;

    @Setter
    private String imageVersion;

    @Setter
    @Getter
    private int width = 0;

    @Setter
    @Getter
    private int height = 0;

    public String getImageVersion() {
        if (this.imageVersion == null || this.imageVersion.length() <= 0) {
            return null;
        }

        return this.imageVersion;
    }

    public String getImageUrl() {
        if (this.imageVersion == null || this.imageVersion.length() <= 0) {
            return null;
        }
        System.out.println(Image.BASE_URL + getImagePath());
        return Image.BASE_URL + getImagePath();
    }

    @JsonIgnore
    public abstract String getImagePath();


    @JsonIgnore
    @Getter
    @Setter
    private InputStream data;

    @JsonIgnore
    @Getter
    @Setter
    private String contentType;

    @JsonIgnore
    @Getter
    @Setter
    private Long contentLength;

    private ImageValidator imageValidator;

    public void prepare(MultipartFile image) {
        imageValidator = new ImageValidator();
        String imageType = image.getContentType().split("/")[0];

//		if (!imageType.equalsIgnoreCase("image") ||
        if (imageValidator.validate(image.getOriginalFilename())) {
            throw new BadRequestException("It's NOT an image");
        }

        try {
            ImageInformation info = readImageInformation(image);
            width = info.width;
            height = info.height;

//            if (info.orientation == ImageInformation.UNKNOWN_ORIENTATION) {
//                this.data = image.getInputStream();
//                this.contentType = image.getContentType();
//                this.contentLength = image.getSize();
//                return;
//            }

            AffineTransform affineTransform = getExifTransformation(info);
            BufferedImage outputImage = transformImage(ImageIO.read(image.getInputStream()), affineTransform, info);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ImageIO.write(outputImage, image.getContentType().replace("image/", ""), bos);
            byte[] bytes = bos.toByteArray();
            this.data = new ByteArrayInputStream(bytes);

            this.contentType = image.getContentType();
            this.contentLength = (long) bytes.length;

        } catch (IOException e) {
            throw new RuntimeException();
        } catch (MetadataException e) {
            e.printStackTrace();
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ImageInformation {
        public static final int UNKNOWN_ORIENTATION = -1;
        public final int orientation;
        public final int width;
        public final int height;

        public ImageInformation(int orientation, int width, int height) {
            this.orientation = orientation;
            this.width = width;
            this.height = height;
        }

        public String toString() {
            return String.format("%dx%d,%d", this.width, this.height, this.orientation);
        }
    }


    public static ImageInformation readImageInformation(MultipartFile image) throws IOException, MetadataException, ImageProcessingException {
        Metadata metadata = ImageMetadataReader.readMetadata(image.getInputStream());
        Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        JpegDirectory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
        PngDirectory pngDirectory = metadata.getFirstDirectoryOfType(PngDirectory.class);
        int orientation;
        try {
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        } catch (Exception e) {
            System.out.println("Could not get orientation");
            orientation = ImageInformation.UNKNOWN_ORIENTATION;
        }

        int width = 0;
        int height = 0;

        if (jpegDirectory != null) {
            width = jpegDirectory.getImageWidth();
            height = jpegDirectory.getImageHeight();
        } else if (pngDirectory != null) {
            width = pngDirectory.getInt(PngDirectory.TAG_IMAGE_WIDTH);
            height = pngDirectory.getInt(PngDirectory.TAG_IMAGE_HEIGHT);
        }

        return new ImageInformation(orientation, width, height);
    }

    public static AffineTransform getExifTransformation(ImageInformation info) {

        AffineTransform t = new AffineTransform();

        switch (info.orientation) {
            case 1:
                break;
            case 2: // Flip X
                t.scale(-1.0, 1.0);
                t.translate(-info.width, 0);
                break;
            case 3: // PI rotation
                t.translate(info.width, info.height);
                t.rotate(Math.PI);
                break;
            case 4: // Flip Y
                t.scale(1.0, -1.0);
                t.translate(0, -info.height);
                break;
            case 5: // - PI/2 and Flip X
                t.rotate(-Math.PI / 2);
                t.scale(-1.0, 1.0);
                break;
            case 6: // -PI/2 and -width
                t.translate(info.height, 0);
                t.rotate(Math.PI / 2);
                break;
            case 7: // PI/2 and Flip
                t.scale(-1.0, 1.0);
                t.translate(-info.height, 0);
                t.translate(0, info.width);
                t.rotate(3 * Math.PI / 2);
                break;
            case 8: // PI / 2
                t.translate(0, info.width);
                t.rotate(3 * Math.PI / 2);
                break;
        }

        return t;
    }

    public static BufferedImage transformImage(BufferedImage image, AffineTransform transform, ImageInformation info) throws Exception {
        int w0 = image.getWidth();
        int h0 = image.getHeight();
        int w1 = w0;
        int h1 = h0;

        int numquadrants = (int) info.orientation;
        if (numquadrants % 2 == 0) {
            w1 = h0;
            h1 = w0;
        }

        AffineTransformOp opRotated = new AffineTransformOp(transform,
                AffineTransformOp.TYPE_BILINEAR);

        BufferedImage transformedImage = new BufferedImage(w1, h1,
                image.getType());

        transformedImage = opRotated.filter(image, transformedImage);
        return transformedImage;
    }
}
