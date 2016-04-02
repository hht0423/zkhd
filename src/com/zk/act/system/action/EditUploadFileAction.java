package com.zk.act.system.action;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import com.inveno.util.PropertyUtils;
import com.inveno.util.UploadFileUtil;
import com.zk.common.baseclass.BaseAction;


/**
 * html编辑器上传文件Action
 *
 * 
 */
@SuppressWarnings( { "static-access", "serial" })
public class EditUploadFileAction extends BaseAction {
	private String uploadContentType;
	private File upload;
	private String uploadFileName;
	private String fileurl;

	/**
	 * 编辑器上传图�?
	 * @Date 2008-7-4
	 * @return
	 */
	public String editUploadFile() {
		if (upload != null && uploadContentType.startsWith("image")) {
			String filePath = PropertyUtils.getProperty("upload");
			String fileName = System.currentTimeMillis() + "0" + uploadFileName;
			String fileUrl = filePath  + "/product/";
			//�?2012-12-07 将路径中的大小字符转换成大写  liming修改�?
			fileurl = UploadFileUtil.uploadFile(upload,fileUrl, fileName.toLowerCase());
			String path = ServletActionContext.getServletContext()
					.getContextPath();
			// 应用�?
			fileurl = path + fileurl;
			
			
		}

		return this.SUCCESS;
	}

	/**
	 * @return the uploadContentType
	 */
	public String getUploadContentType() {
		return uploadContentType;
	}

	/**
	 * @param uploadContentType
	 *            the uploadContentType to set
	 */
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	/**
	 * @return the upload
	 */
	public File getUpload() {
		return upload;
	}

	/**
	 * @param upload
	 *            the upload to set
	 */
	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * @return the uploadFileName
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * @param uploadFileName
	 *            the uploadFileName to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * @return the fileurl
	 */
	public String getFileurl() {
		return fileurl;
	}

	/**
	 * @param fileurl
	 *            the fileurl to set
	 */
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
}
