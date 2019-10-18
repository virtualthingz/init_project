package kr.supergate.shoppingfolder.controller.app;


import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.supergate.shoppingfolder.common.XHttpHeader;
import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.domain.account.login.UserBody;
import kr.supergate.shoppingfolder.domain.account.login.UserLogin;
import kr.supergate.shoppingfolder.domain.account.register.RegisterUserBody;
import kr.supergate.shoppingfolder.exception.NotImplementedException;
import kr.supergate.shoppingfolder.exception.account.SigninAccountException;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import kr.supergate.shoppingfolder.service.AccountRegisterService;
import kr.supergate.shoppingfolder.service.AccountService;
import kr.supergate.shoppingfolder.service.UserService;
import kr.supergate.shoppingfolder.util.JsonSerializationUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.util.CookieGenerator;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by Hang Jo on 2019.10.10
 */
@RestController
@Api(value = "accounts")
@RequestMapping(value = "/app/accounts")
public class AppAccountController {

    final Logger logger = LoggerFactory.getLogger(AppAccountController.class);

    @Autowired
    AccountRegisterService accountRegisterService;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;



    /**
     * Register
     * @param registerUserBody
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "X-Session", required = false, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "X-Device", required = false, dataType = "string", paramType = "header", defaultValue = "Andriod", allowMultiple = true)})
    @ApiOperation(value = "Register new user from [LOGIN_ID]")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public User register(@ApiParam(
            value = "회원 가입<br />"
                    + "하기 Numbering 으로 약관 ID 참조<br />"
                    + "1. (필수) 서비스 이용약관 <br />"
                    + "2. (공통) 개인정보 수집/이용 동의 <br />"
                    + "3. (필수) 개인정보 수집/이용 동의 <br />"
                    + "4. (선택) 개인정보 수집/이용 동의 <br />"
                    + "5. (선택) 혜택알림 수신동의 <br />"
                    + "6. (선택) 위치정보 수집/이용 및 위치기반서비스 이용약관 동의 <br />"
                    + "7. (공통) 오픈소스 라이센스 <br />"
                    + "8. (필수) 14세 이상 확인 <br />"
    )
                         @Valid @RequestBody RegisterUserBody registerUserBody) {
        return accountRegisterService.register(registerUserBody);
    }


    /**
     * Sign in
     * @param guestMemberId
     * @param userBody
     * @param response
     * @return
     * @throws IOException
     */
    @com.wordnik.swagger.annotations.ApiOperation(value = "Signin from [GOOGLE, LOGIN_ID]")
    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public User signin(@com.wordnik.swagger.annotations.ApiParam(value = "가상 유저용 signin id") @RequestParam(required = false) String guestMemberId,
                       @com.wordnik.swagger.annotations.ApiParam(value = "[GOOGLE, LOGIN_ID] signin JSON 메타데이터 <br /> SYRUP, GOOGLE 의 경우 동의약관 ID 목록 필요 <br /><br /> "
                               + "[GOOGLE]  <br /> {  \"authType\": \"GOOGLE\",  \"ssoUser\": {    \"ssoSerial\": \"fb_user_uniq_key\", \"name\": \"이름\" , \"agreedClauses\": [ 1, 2, 3 ]} <br /><br /> "
                               + "[LOGIN_ID] <br /> {  \"authType\": \"LOGIN_ID\",  \"loginUser\": {    \"loginId\": \"login_id\",    \"password\": \"password\" }} <br /><br /> "
                               , allowMultiple = true)
//                       @RequestParam(required = false, defaultValue = "{}") String userParam,
                       @RequestBody(required = true) UserBody userBody,
                       HttpServletResponse response) throws IOException
    {

//        UserBody userBody = getLoginUserBody(userParam);
        List<Integer> clauses = userBody.getAgreedClauses();

        System.out.println("=============== signin =================");
        System.out.println("=> auth type : " + userBody.getAuthType());
        System.out.println("=> sso serial : " + userBody.getSsoUser().getSsoSerial());
        System.out.println("=> sso name : " + userBody.getSsoUser().getName());

        // sign in
        User user = accountService.signin(userBody);

        if(user == null)
            throw new SigninAccountException(ErrorMessage.INVALID_SIGNIN_INFO);

        // make cookie
        generateSessionCookie(response, user.getSession());

//        if(!(user instanceof UserLogin)) {
//            user = userService.getUser(user.getUserId());
//        }

        return user;
    }


    /**
     * select user by id
     * @param id
     * @param authType
     * @return
     */
    @ApiOperation(value = "Get a user from [LOGIN_ID]")
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    public User getUser(@PathVariable String id, @ApiParam(allowableValues = "LOGIN_ID") @RequestParam User.AuthType authType) {
        if(User.AuthType.LOGIN_ID.equals(authType))
            return userService.getUserByloginId(id);

//        throw new NotImplementedException("지원하지 않는 기능입니다");
        return null;
    }




    private UserBody getLoginUserBody(String loginMetaData) {
        try {
            return JsonSerializationUtil.getStrictJsonReader(UserBody.class).readValue(loginMetaData);
        } catch (Exception e) {
            logger.debug("login data pase error : " + loginMetaData);
            throw new SigninAccountException(ErrorMessage.INVALID_SIGNIN_INFO);
        }
    }

    private void generateSessionCookie(HttpServletResponse response, String session) {
        CookieGenerator cookieGenerator = new CookieGenerator();

        cookieGenerator.setCookieDomain("supergate.kr");

        cookieGenerator.setCookiePath("/");

        cookieGenerator.setCookieName(XHttpHeader.X_SESSION);
        cookieGenerator.addCookie(response, session);
    }
}
