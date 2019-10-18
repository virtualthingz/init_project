package kr.supergate.shoppingfolder.controller.admin;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import kr.supergate.shoppingfolder.common.FileUtils;
import kr.supergate.shoppingfolder.domain.Shoppingmall;
import kr.supergate.shoppingfolder.domain.Storage;
import kr.supergate.shoppingfolder.service.ShoppingmallService;
import kr.supergate.shoppingfolder.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
@Api(value = "shoppingmall")
@RequestMapping(value = "/admin/shoppingmalls")
public class AdminShoppingmallController {

    @Autowired
    ShoppingmallService shoppingmallService;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    StorageService storageService;

    @Autowired
    Storage storage;

    @ApiOperation(value = "create shoppingmall")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Shoppingmall insertShoppingmall(
            @RequestParam(value = "name", required = true) String name,
            @ApiParam(value = "banner image") @RequestParam MultipartFile bannerImage,
            @ApiParam(value = "detail image") @RequestParam MultipartFile detailImage,
            @ApiParam(value = "width of image") @RequestParam(required = false, defaultValue = "0") int width,
            @ApiParam(value = "height of image") @RequestParam(required = false, defaultValue = "0") int height,
            @ApiParam(value = "display order") @RequestParam int displayOrder,
            MultipartHttpServletRequest multipartHttpServletRequest)
    {
        Shoppingmall shoppingmall = new Shoppingmall();
        shoppingmall.setName(name);
        shoppingmall.setBannerUrl(bannerImage.getName());
        shoppingmall.setDetailUrl(bannerImage.getName());
        shoppingmall.setDisplayOrder(displayOrder);
        shoppingmall.setBannerUrl(storage.getUrl() + bannerImage.getOriginalFilename());
        shoppingmall.setDetailUrl(storage.getUrl() + detailImage.getOriginalFilename());
        System.out.println(shoppingmall.getBannerUrl());
        System.out.println(shoppingmall.getDetailUrl());
        fileUtils.fileSave(multipartHttpServletRequest, width, height);
        shoppingmallService.insertShoppingmall(shoppingmall);

        return shoppingmall;
    }

    @ApiOperation(value = "List of shoppingmall")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Shoppingmall> getShoppingmalls()
    {
        return shoppingmallService.selectShoppingmalls();
    }


    @ApiOperation(value = "List of shoppingmall")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Shoppingmall getShoppingmalls(
            @RequestParam(value = "id", required = false) String id) {
        return shoppingmallService.selectShoppingmall(id);
    }


    @ApiOperation(value = "Delete an shoppingmall")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteFreeImage(
            @ApiParam(name = "id", required = true, value = "free image id") @PathVariable("id") String id) {

        shoppingmallService.deleteShoppingmall(id);
    }
}
