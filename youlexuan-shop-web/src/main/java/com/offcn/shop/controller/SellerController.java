package com.offcn.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.pojo.TbSeller;
import com.offcn.sellergoods.service.SellerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 商户controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;

	@Autowired
	private JavaMailSenderImpl mailSender;

	@RequestMapping("/sendMail")
	public Result sendMail(String email){
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			Jedis jedis = new Jedis("192.168.188.128", 6379);
			jedis.auth("123");
			Random random = new Random();
			int i = random.nextInt(900000)+100000;
			jedis.set("number",i+"");
			mimeMessageHelper.setFrom("kyrie.yangy@foxmail.com");
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setText(i+"");
			mailSender.send(mimeMessage);
			return new Result(true,"验证码发送成功");
		}catch (MessagingException e) {
			e.printStackTrace();
			return new Result(false,"验证码发送失败");
		}
	}

	@RequestMapping("/resetPassword")
	public Result resetPassword(@RequestBody Map map){
		Jedis jedis = new Jedis("192.168.188.128", 6379);
		jedis.auth("123");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String number = jedis.get("number");
		String number1 = (String) map.get("number");
		String password = (String) map.get("password");
		if (number.equals(number1)){
			String sellerId = (String) map.get("sellerId");
			TbSeller seller = new TbSeller();
			String encode = passwordEncoder.encode(password);
			seller.setPassword(encode);
			seller.setSellerId(sellerId);
			sellerService.update(seller);
			return new Result(true,"密码重置成功");
		}else{
			return new Result(false,"验证码输入错误");
		}
	}

	@RequestMapping("/updatePassword")
	public Result updatePassword(@RequestBody Map map){
		try {
			String oldPassword = (String) map.get("oldPassword");
			String newPassword = (String) map.get("newPassword");
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			TbSeller one = sellerService.findOne(sellerId);
			String password = one.getPassword();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(passwordEncoder.matches(oldPassword,password)){
				String encode = passwordEncoder.encode(newPassword);
				TbSeller seller = new TbSeller();
				seller.setSellerId(sellerId);
				seller.setPassword(encode);
				sellerService.update(seller);
				return new Result(true,"密码修改成功，请重新登录");
			}else{
				return new Result(false,"原密码输入错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"密码修改失败");
		}
	}
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeller seller){
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encode = passwordEncoder.encode(seller.getPassword());
			seller.setPassword(encode);
			sellerService.add(seller);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeller seller){
		try {
			sellerService.update(seller);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSeller findOne(String sellerId){
		return sellerService.findOne(sellerId);		
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(String [] sellerIds){
		try {
			sellerService.delete(sellerIds);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
		return sellerService.findPage(seller, page, rows);		
	}

	@Test
	public void test(){
//		System.out.println(new BCryptPasswordEncoder().matches("offcn","$2a$10$0rbs5aFvMPYMziBQxucU.eiSPupIskNz.mpylasM3mx7T6CGO9jea"));
		Random random = new Random();
		int i = random.nextInt(900000)+100000;
		System.out.println(i);

	}

}
