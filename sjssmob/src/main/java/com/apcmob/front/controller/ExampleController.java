package com.apcmob.front.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apcmob.front.service.ExampleService;
import com.apcmob.object.ExampleBO;
import com.apcmob.util.AES256Cipher;
import com.apcmob.util.SeedCBC;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * ExampleController
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
@Controller
public class ExampleController {

	private final Log log = LogFactory.getLog(getClass());

	@Resource(name = "exampleService")
	private ExampleService exampleService;

	/**
	 * 예제 페이지 호출
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/example.do")
	public String searchUserTestForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			HttpSession session) throws Exception {
		log.info("test!!!!!!!!!!!!!!!!");

		AES256Cipher cipher = new AES256Cipher();
//		String test = cipher.AES_Encode("test1234");
//		System.out.println("AES256 TEST ::: " + test);
//		System.out.println("AES256 TEST ::: " + cipher.AES_Decode(test));
		
		String sPlainText = "CEMSUSER";
		String sCipherText = "";

		BASE64Encoder base64Encoder = new BASE64Encoder();
		BASE64Decoder base64Decoder = new BASE64Decoder();

		SeedCBC s = new SeedCBC();

		sCipherText = base64Encoder.encode(s.Encryption(sPlainText.getBytes()));
		
		//db test
		ExampleBO exampleBO = exampleService.exampleTest("");
		modelMap.addAttribute("exampleKey", exampleBO.getTest());
		modelMap.addAttribute("aes256", "");
		modelMap.addAttribute("seedCBC", sCipherText);
		modelMap.addAttribute("ipaddr", java.net.InetAddress.getLocalHost().getHostAddress());
		log.info("test!!!!!!!!!!!!!!!!!! == " + exampleBO.getTest());

		log.info("1test!!!!!!!!!!!!!!!!!! == " + request.getRemoteAddr());
		log.info("2test!!!!!!!!!!!!!!!!!! == " + request.getServerName());
		log.info("3test!!!!!!!!!!!!!!!!!! == " + java.net.InetAddress.getLocalHost().getHostAddress());
		
		//session 제거
//		session.removeAttribute("ACCOUNT");

//		//관리자 정보
//		AdminBO adm_user = (AdminBO) session.getAttribute("ADMIN_ACCOUNT");
//		modelMap.addAttribute("adm_user", adm_user);
//		
//		//사용자 정보
//		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
//		modelMap.addAttribute("user", user);
		
		return "main/example";

	}

}
