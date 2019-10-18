package kr.supergate.shoppingfolder.controller.callback;

import com.wordnik.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@Api(value = "inicis")
@RequestMapping(value = "/callback/inicis")
public class CallbackInicisController {

//	@Autowired
//	private InicisService inicisService;
//
//	@Autowired
//	private SalesService salesService;
//
//	@Autowired
//	private com.skplanet.syrupfashion.service.locale.cn.InicisService cn_inicisService;
//
//	@Autowired
//	private SmsService smsService;


	@RequestMapping(value="visa3d", method= RequestMethod.POST)
	public String visa3d(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam("P_STATUS") String status,
                         @RequestParam("P_RMESG1") String msg,
                         @RequestParam("P_TID") String tid,
                         @RequestParam("P_REQ_URL") String reqUrl,
                         @RequestParam("P_NOTI") String noti,

                         @RequestParam(value = "P_MID", required = false) String mid,
                         @RequestParam(value = "P_AUTH_DT", required = false) String authDt,
                         @RequestParam(value = "P_TYPE", required = false) String type,
                         @RequestParam(value = "P_OID", required = false) String oid,
                         @RequestParam(value ="P_AUTH_NO", required = false) String authNo,
                         @RequestParam(value = "P_AMT", required = false) String amt,
                         @RequestParam(value = "P_KOR_AMT", required = false) String korAmt,
                         @RequestParam(value = "P_RESULTCD", required = false) String resultCD,  // 결과코드
                         @RequestParam(value = "P_RESULTMSG", required = false) String resultMsg,   // 결과메세지
                         @RequestParam(value = "P_EXRATE", required = false) String exRate,  // 환율
                         @RequestParam(value = "P_HASH", required = false) String hash  // 해쉬검증  ex) P_OID+P_AMT+P_AUTH_NO+해쉬키 SHA512
	) {

		System.out.println("############################# visa3d Noti ##################################");


		System.out.println("request : "+request);
		System.out.println("response : "+response);

		System.out.println("visa 3d status = [" + status + "], msg = [" + msg + "], tid = [" + tid + "], reqUrl = [" + reqUrl + "], noti = [" + noti + "]");

//		Visa3dResult visa3dResult = new Visa3dResult(tid, reqUrl, noti);
//		inicisService.processVisa3d(visa3dResult);

		return "OK";
	}



//	Attribute Name - P_TID, Value - INIMX_CARDINIpayTest20150514114554401453
//	Attribute Name - P_MID, Value - INIpayTest
//	Attribute Name - P_AUTH_DT, Value - 20150514114554
//	Attribute Name - P_STATUS, Value - 01
//	Attribute Name - P_TYPE, Value - CARD
//	Attribute Name - P_OID, Value - S228
//	Attribute Name - P_FN_CD1, Value - 14
//	Attribute Name - P_FN_CD2, Value - 14
//	Attribute Name - P_FN_NM, Value - ����ī��
//	Attribute Name - P_AMT, Value - 500
//	Attribute Name - P_UNAME, Value - 6
//	Attribute Name - P_RMESG1, Value - �ݾ� ����(1000�� �̸� �ſ�ī�� ���� �Ұ�)
//	Attribute Name - P_RMESG2, Value - 00
//	Attribute Name - P_NOTI, Value -
//	Attribute Name - P_AUTH_NO, Value -
//	Attribute Name - P_CARD_ISSUER_CODE, Value -
//	Attribute Name - P_CARD_NUM, Value - 451843*********5
//	Attribute Name - P_CARD_MEMBER_NUM, Value -
//	Attribute Name - P_CARD_PURCHASE_CODE, Value -
//	Attribute Name - P_PRTC_CODE, Value -
//	Attribute Name - P_CARD_PURCHASE_NAME, Value -
//	Attribute Name - P_CARD_ISSUER_NAME, Value -
//	Attribute Name - P_RMESG3, Value - RM3_DISC_AMT^|RM3_PRICE^500|RM3_ORG_AMT^|RM3_EVENT_CODE^|RM3_INTEREST^0
//	Attribute Name - P_MERCHANT_RESERVED, Value - dXNlcG9pbnQ9MCY=

	@RequestMapping(value="isp", method= RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String isp(HttpServletRequest request,
                      @RequestParam("P_STATUS") String status,
                      @RequestParam("P_TID") String tid,
                      @RequestParam("P_TYPE") String type,
                      @RequestParam("P_AUTH_DT") String authDt,

                      @RequestParam("P_MID") String mid,
                      @RequestParam("P_OID") String oid,
                      @RequestParam("P_AMT") String amt,
                      @RequestParam(value = "P_KOR_AMT", required = false) String korAmt,

                      @RequestParam(value = "P_RESULTCD", required = false) String resultCD,  // 결과코드
                      @RequestParam(value = "P_RESULTMSG", required = false) String resultMsg,   // 결과메세지
                      @RequestParam(value = "P_EXRATE", required = false) String exRate,  // 환율
                      @RequestParam(value = "P_HASH", required = false) String hash,  // 해쉬검증  ex) P_OID+P_AMT+P_AUTH_NO+해쉬키 SHA512

                      @RequestParam(value = "P_UNAME", required = false) String uname,
                      @RequestParam(value = "P_MNAME", required = false) String mname,

                      @RequestParam(value ="P_RMESG1", required = false) String rmsg1,  // 지불 결과 메세
                      @RequestParam(value ="P_RMESG2", required = false) String rmsg2,  // 할부개월
                      @RequestParam(value ="P_RMESG3", required = false) String rmsg3,  // 할부개월

                      @RequestParam(value ="P_FN_CD1", required = false) String fnCd, // 카드코
                      @RequestParam(value ="P_NOTI", required = false) String noti,
                      @RequestParam(value ="P_AUTH_NO", required = false) String authNo,

                      @RequestParam(value = "P_CARD_ISSUER_CODE",required = false) String cardIssuerCode,
                      @RequestParam(value = "P_CARD_NUM",required = false) String cardNum,
                      @RequestParam(value = "P_CARD_MEMBER_NUM",required = false) String cardMemberNum,

                      @RequestParam(value = "P_CARD_PURCHASE_CODE",required = false) String cardPurchaseCode,
                      @RequestParam(value = "P_PRTC_CODE",required = false) String cardPrtcCode,

                      @RequestParam(value = "P_CARD_PURCHASE_NAME",required = false) String cardPurchaseName,
                      @RequestParam(value = "P_CARD_ISSUER_NAME",required = false) String cardIssuerName,
                      @RequestParam(value = "P_CARD_INTEREST",required = false) String cardInterest,
                      @RequestParam(value = "P_CARD_CHECKFLAG",required = false) String cardCheckFlag) {

		System.out.println("############################# isp Noti ##################################");

		boolean isSendSms = true;
//		if (!inicisService.isNewTid(tid)) {
//			System.out.println("tid_duplicated : " + tid + ", " + oid + ", " + uname);
//			isSendSms = false;
////			return "OK";
//		}

		Enumeration enParams = request.getParameterNames();
		while(enParams.hasMoreElements()){
			String paramName = (String)enParams.nextElement();
			System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		}

		System.out.println("isp status = [" + status + "], " +
				" = [" + tid + "], type = [" + type + "], authDt = [" + authDt + "], mid = [" + mid + "], oid = [" + oid + "], amt = [" + amt + "], uname = [" + uname + "], mname = [" + mname + "], rmsg1 = [" + rmsg1 + "], rmsg2 = [" + rmsg2 + "], fnCd = [" + fnCd + "], noti = [" + noti + "], authNo = [" + authNo + "], cardIssuerCode = [" + cardIssuerCode + "], cardMemberNum = [" + cardMemberNum + "], cardPurchaseCode = [" + cardPurchaseCode + "], cardPrtcCode = [" + cardPrtcCode + "], cardInterest = [" + cardInterest + "], cardCheckFlag = [" + cardCheckFlag + "]");

		//isp status = [00], tid = [INIMX_CARDINIpayTest20150410221549704808], type = [CARD], authDt = [20150410221549], mid = [INIpayTest], oid = [S163], amt = [1000], uname = [1], mname = [null], rmsg1 = [����ó���Ǿ����ϴ�.], rmsg2 = [00], fnCd = [17], noti = [], authNo = [33060052], cardIssuerCode = [81], cardMemberNum = [8101358703], cardPurchaseCode = [17], cardPrtcCode = [null], cardInterest = [null], cardCheckFlag = [null]

//		IspResult ispResult = new IspResult(status, tid, type, mid, oid, amt, uname, mname, rmsg1, rmsg2, fnCd,authDt ,noti,authNo,
//				cardIssuerCode, cardMemberNum, cardPurchaseCode, cardPrtcCode, cardInterest, cardCheckFlag,rmsg3, cardNum, cardPurchaseName, cardIssuerName);
//
//		System.out.println("oid :"+ispResult.getOid());
//		// 일단 결제정보저장
//		IspResult ispInserted =inicisService.insertInicis(ispResult);
//
//		if (!checkSucess(status, rmsg1, noti)){
//			return "OK";
//		}
//
//		boolean isSucceed = inicisService.processIsp(ispInserted, isSendSms);
//		if(!isSucceed) {
//			inicisService.cancelIsp(ispInserted);
//		}

		return "OK";
	}

}
