//package com.wl.springboot.controller;
//
//import cn.hutool.core.date.DateTime;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.wl.springboot.config.RabbitMQConfig;
//import com.wl.springboot.service.Consumer;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import com.wl.springboot.domain.User;
//import com.wl.springboot.service.UserService;
//import com.github.pagehelper.PageInfo;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//
//	@Autowired
//	private UserService userService;
//
//
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//	@Autowired
//	private Consumer consumer;
//	/*添加数据*/
//	@RequestMapping("/add")
//	@ResponseBody
//	public String add(String id,String userName){
//		User u = new User();
//		u.setId(id);
//		u.setUserName(userName);
//		this.userService.insertUser(u);
//		return u.getId()+"    " + u.getUserName();
//	}
//	/*实现mybatis的批量插入*/
//	@RequestMapping(value = "/addbatch",method = RequestMethod.POST)
//	@ResponseBody
//	public String addbatch(@RequestBody List<User> list)throws JsonProcessingException {
//		int i = userService.insertUserBatch(list);
//		if(i>=1){
//			return "新增了"+i+"条数据";
//		}else{
//			return "批量新增失败";
//		}
//	}
//
//	/*生产者发送消息*/
//	@GetMapping("/sendMessage")
//	public Object sendMessage() {
//
//		List<Thread> list = new ArrayList<>();
//		for (int i = 0; i <10 ; i++) {
//			Thread t = new Thread(()->{
//				String value = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
//				log.info("send message {}", value);
//				rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, value);
//			});
//			list.add(t);
//		}
//		list.forEach(Thread::start);
//
//		return "ok";
//	}
//
//	@GetMapping("/getList")
//	@ResponseBody
//	public List<User> getList(){
//		List<User> list = consumer.getList();
//		return list;
//	}
//
//
//	/*文件上传*/
//	@RequestMapping(value = "/upload")
//	public String upload(MultipartFile file) {
//		try {
//			if (file.isEmpty()) {
//				return "file is empty";
//			}
//			String fileName = file.getOriginalFilename();//获取文件名
//			String suffixName = fileName.substring(fileName.lastIndexOf("."));//切割文件名
//			log.info("上传的文件名为：" + fileName + " 后缀名为" + suffixName);
//			// 设置文件存储路径(D盘),你可以存放在你想要指定的路径里面。
//			String filePath = "D:/upload";
//			//文件存放路径=filePath+/+ fileName
//			String path = filePath + "/" + fileName;
//			File dest = new File(path);
//			// 检测是否存在目录
//			if (!dest.getParentFile().exists()) {
//				dest.getParentFile().mkdirs();// 新建文件夹
//			}
//			file.transferTo(dest);// 文件写入到上述的存放路径path
//			return "upload success";
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "upload failure";
//	}
//
//
//	/*
//	* 文件的下载
//	* */
////	@RequestMapping("/downLoadTemplateExcel")
////	public Info downLoadTemplateExcel(HttpServletResponse response){
////		log.info("/customAnalysisConfig/downLoadTemplateExcel");
////		Info infos=new Info();
////		// 创建输入输出流
////		BufferedInputStream bis = null;
////		BufferedOutputStream bos = null;
////		//String url = "C:/Users/admin/Desktop/test.xlsx";
////		String url =null;
////		if(environment.equals("local")){//本地文件路径
////			url ="C:/Users/admin/Desktop/test.xlsx";
////		}else {//服务器文件路径
////			url = "/usr/java/test.xlsx";
////		}
////		String downLoadPath = url;
////		String fileName="template.xlsx";//生成的文件名
////		File file2 = new File(downLoadPath);//要下载的文件对象
////		if (!file2.exists()) {//如果目录不存在,创建目录
////			file2.mkdirs();
////		}
////		long fileLength = file2.length();// 获取文件长度
////		try {
////			//Content-Disposition: attachment; filename="filename.xls"
////			//第一个参数是attachment（意味着消息体应该被下载到本地；大多数浏览器会呈现一个“保存为”的对话框，
////			// 将filename的值预填为下载后的文件名，假如它存在的话）
////			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
////			//Content-Type 实体头部用于指示资源的MIME类型 media type
////			response.setHeader("Content-Type", "application/json");
////			//Content-Length, HTTP消息长度, 用十进制数字表示的八位字节的数目
////			response.setHeader("Content-Length", String.valueOf(fileLength));
////			// 创建输入输出流实例
////			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
////			bos = new BufferedOutputStream(response.getOutputStream());
////			// 创建字节缓冲大小
////			byte[] buff = new byte[2048];
////			int bytesRead;
////			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
////				bos.write(buff, 0, bytesRead);
////			}
////		}catch (Exception e){
////			e.printStackTrace();
////		}finally {
////			if (bis != null)
////				try {
////					bis.close();// 关闭输入流
////				} catch (IOException e) {
////					e.printStackTrace();
////				}
////			if (bos != null)
////				try {
////					bos.close();// 关闭输出流
////				} catch (IOException e) {
////					e.printStackTrace();
////				}
////		}
////		infos.setCode("1");
////		infos.setMsg("成功");
////		return infos ;
////	}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//	/**
//	 * 测试读
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping("/get/{id}")
//	@ResponseBody
//	public String findById(@PathVariable("id") String id){
//		User u = this.userService.findById(id);
//		return u.getId()+"    " + u.getUserName();
//	}
//	/**
//	 * 测试写然后读
//	 * @param id
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/addAndRead")
//	@ResponseBody
//	public String addAndRead(String id,String userName){
//		User u = new User();
//		u.setId(id);
//		u.setUserName(userName);
//		this.userService.wirteAndRead(u);
//		return u.getId()+"    " + u.getUserName();
//	}
//
//	/**
//	 * 测试读然后写
//	 * @param id
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/readAndAdd")
//	@ResponseBody
//	public String readAndWrite(String id,String userName){
//		User u = new User();
//		u.setId(id);
//		u.setUserName(userName);
//		this.userService.readAndWirte(u);
//		return u.getId()+"    " + u.getUserName();
//	}
//
//	/**
//	 * 测试分页插件
//	 * @return
//	 */
//	@RequestMapping("/queryPage")
//	@ResponseBody
//	public PageInfo queryPage(){
//		PageInfo<User> page = this.userService.queryPage("master", 1, 5);
//		StringBuilder sb = new StringBuilder();
//		sb.append("<br/>总页数=" + page.getPages());
//		sb.append("<br/>总记录数=" + page.getTotal()) ;
//		for(User u : page.getList()){
//			sb.append("<br/>" + u.getId() + "      " + u.getUserName());
//		}
//		System.out.println("分页查询....\n" + sb.toString());
//		return page;
//	}
//
//	@RequestMapping("/wirteAndReadPlug")
//	public String wirteAndReadPlug(String id,String userName){
//		User u = new User();
//		u.setId(id);
//		u.setUserName(userName);
//		this.userService.wirteAndReadPlug(u);
//		return u.getId()+"    " + u.getUserName();
//	}
//
//}
