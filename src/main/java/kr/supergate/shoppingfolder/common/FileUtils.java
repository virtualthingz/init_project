package kr.supergate.shoppingfolder.common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import kr.supergate.shoppingfolder.domain.Storage;
import kr.supergate.shoppingfolder.dto.BoardFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;


@Component
public class FileUtils {

	@Autowired
	Storage storage;
	
	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
			return null;
		}
		
		List<BoardFileDto> fileList = new ArrayList<>();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd"); 
    	ZonedDateTime current = ZonedDateTime.now();
    	String path = "images/"+current.format(format);
    	File file = new File(path);
		if(file.exists() == false){
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()){
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for (MultipartFile multipartFile : list){
				if(multipartFile.isEmpty() == false){
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)){
						break;
					}
					else{
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else{
							break;
						}
					}
					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
	
	public List<BoardFileDto> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
			return null;
		}
		
		List<BoardFileDto> fileList = new ArrayList<>();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd"); 
    	ZonedDateTime current = ZonedDateTime.now();
    	String path = "images/"+current.format(format);
    	File file = new File(path);
		if(file.exists() == false){
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()){
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for (MultipartFile multipartFile : list){
				if(multipartFile.isEmpty() == false){
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)){
						break;
					}
					else{
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else{
							break;
						}
					}
					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}

	public void saveFile(MultipartFile multipartFile) {

		String originalFilename = multipartFile.getOriginalFilename();
		String path = storage.getLocation();

		File file = new File(path);
		if(file.exists() == false){
			file.mkdirs();
		}

		try {
			file = new File(path + "/" + originalFilename);
			multipartFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Object fileSave(MultipartHttpServletRequest multipartRequest, int width, int height) {

		List<HashMap> fileArrayList = new ArrayList<HashMap>();
		HashMap fileHashMap;

		String filePath = storage.getLocation(); //파일 저장 경로, 설정파일로 따로 관리한다.

		File dir = new File(filePath); //파일 저장 경로 확인, 없으면 만든다.
		if (!dir.exists()) {
			dir.mkdirs();
		}

		Iterator<String> itr =  multipartRequest.getFileNames(); //파일들을 Iterator 에 넣는다.

		while (itr.hasNext()) { //파일을 하나씩 불러온다.

			MultipartFile mpf = multipartRequest.getFile(itr.next());

			fileHashMap = new HashMap();

			String originalFilename = mpf.getOriginalFilename(); //파일명

			String fileFullPath = filePath+"/"+originalFilename; //파일 전체 경로

			try {
				File file = new File(fileFullPath);
				if (width != 0 && height != 0) {
					confirmImageSizeAndResize(file, width, height);
				}

				//파일 저장
				mpf.transferTo(new File(fileFullPath)); //파일저장

				fileHashMap.put("originalFilename", originalFilename);
				fileHashMap.put("fileFullPath", fileFullPath);

				fileArrayList.add(fileHashMap);

			} catch (Exception e) {
				System.out.println("postTempFile_ERROR======>"+fileFullPath);
				e.printStackTrace();
			}

		}

		Map<String, Object> retVal = new HashMap<String, Object>(); //응답값 셋팅

		try{
			retVal.put("fileInfoList", fileArrayList);
			retVal.put("code", "OK");
		}catch(Exception e){
			retVal.put("code", "FAIL");
		}

		return retVal;

	}


	private void confirmImageSizeAndResize(File img, int width, int height) throws IOException {

		Image newImage = getScaledImage(img, width, height);
		ImageIO.write(toBufferedImageFrom(newImage, newImage.getWidth(null), newImage.getHeight(null)), "jpg", img);
	}

	private Image getScaledImage(File img, int width, int height) throws IOException{

		Image oldImage = ImageIO.read(img);
		Image newImage = null;
		final int oldWidth = oldImage.getWidth(null);
		final int oldHeight = oldImage.getHeight(null);
		int newHeight = 0;
		int newWidth = 0;

		if (oldHeight < height
				|| oldWidth < width)
			return oldImage;

		if (oldHeight >= height
				|| oldWidth >= width) {
			int longer = Math.max(oldHeight, oldWidth);
			float ratio = Math.round((120f / (float) longer) * 100f) / 100f;
			newHeight = (int) (oldHeight * ratio);
			newWidth = (int) (oldWidth * ratio);
			newImage = oldImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		}
		if (newImage == null)
			throw new IOException("파일 크기 조절에 실패하였습니다.");

		return newImage;
	}

	private BufferedImage toBufferedImageFrom(Image img, int width, int height) {

		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
}