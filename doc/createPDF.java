	@ResponseBody
	@RequestMapping("downloadServiceDetails")
	public String downloadServiceDetails(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		long orderId = Long.parseLong(request.getParameter("orderId"));

		String demoHtmlPath = PublicCacheUtil.getRealPath() + "/WEB-INF/jsp/order/demoMeetingDetails.html";
		String templateHtml = readTemplateHtml(demoHtmlPath);

		// 开始替换模板内容
		templateHtml = templateHtml.replace("", "");
		

		String downloadUrl = createPDF(templateHtml, String.valueOf(orderId));
		map.put("downloadUrl", downloadUrl);
		return CommonUtil.jsonSerialize(map);
	}
	
	public String readTemplateHtml(String htmlPath) {
		String templateHtml = null;
		BufferedReader bufferedReader = null;
		try {
			StringBuffer htmlStrbuf = new StringBuffer();
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(htmlPath), "UTF-8"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				htmlStrbuf.append(line);
			}
			templateHtml = htmlStrbuf.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return templateHtml;
	}
	
	public String createPDF(String templateHtml, String pdfId) {

		// 创建临时文件夹，做为pdf的资源路径
		String folderName = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandomNumber(6)
				+ (pdfId == null ? "" : pdfId);
		String resourceFilePath = PublicCacheUtil.getRealPath() + "/download/pdf/" + folderName + "/";
		File file = new File(resourceFilePath);
		if (!file.exists() && !file.isDirectory()) {
			boolean result = file.mkdirs();
			logger.debug("创建临时文件夹--" + result);
		}

		// createPDF start
		String pdfFileName = folderName + ".pdf";
		String pdfFile = PublicCacheUtil.getRealPath() + "/download/pdf/" + pdfFileName;
		try {
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			// 解决中文支持问题
			fontResolver.addFont(PublicCacheUtil.getRealPath() + "/WEB-INF/PDFtool/simsun.ttf", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			// 解析html生成pdf
			renderer.setDocumentFromString(templateHtml); // 需无bom头的html
			// 解决图片相对路径问题
			renderer.getSharedContext().setBaseURL("file:/" + resourceFilePath);
			renderer.layout();
			renderer.createPDF(new FileOutputStream(pdfFile));
			renderer.finishPDF();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 删除资源文件
		deleteFiles(file);
		// pdf下载路径
		String downloadUrl = PublicCacheUtil.getCtxPath() + "/download/pdf/" + pdfFileName;
		
		return downloadUrl;
	}
	
	/**
	 * 删除文件夹及图片
	 * 
	 * @param file
	 */
	public void deleteFiles(File file) {
		if (!file.exists())
			return;
		if (file.isFile()) {
			file.delete();
			return;
		}
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteFiles(files[i]);
		}
		file.delete();
	}