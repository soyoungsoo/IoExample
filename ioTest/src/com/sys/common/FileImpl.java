package com.sys.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import com.sys.util.FileIO;

public class FileImpl implements FileIO {

	private HttpServletResponse response = null;
	private FileInputStream fis = null;
	private BufferedOutputStream bos = null;
	private BufferedReader br = null;

	int length = 0;

	public FileImpl(HttpServletResponse response) {
		this.response = response;

	}

	@Override
	public void txtPrint(String path) {

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			bos = new BufferedOutputStream(response.getOutputStream());
			while ((length = br.read()) != -1) {
				bos.write(length);
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("해당 인코딩 방식을 지원하지 않습니다.");
		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾지 못하였습니다.");
		} catch (IOException e) {
			System.out.println("입출력 오류 발생하였습니다.");
		} finally {
			closeRepeat();
		}
	}

	@Override
	public void imgPrint(String path) {
		whileRepeat(path);
	}

	@Override
	public void FileDown(String path, String name, String ext) {

		File File = new File(path);
		String[] checkList = new String[] { "image/png", "image/gif", "image/jpg", "image/jpeg", "text/plain" };

		try {
			String Fname = "";
			Fname = URLEncoder.encode(name, "UTF-8");

			response.setHeader("Content-Disposition", "attachment;filename=" + Fname + "." + ext + ";");
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setContentLength((int) File.length());

			Path source = Paths.get(path);
			String mimeType = Files.probeContentType(source);
			
			if (Arrays.asList(checkList).contains(mimeType)){
				whileRepeat(path);
			}else {
				System.out.println("이미지나 텍스트 파일이 아닙니다.");
			}
		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾지 못하였습니다.");
		}catch (IOException e) {
			System.out.println("입출력 오류가 발생하였습니다.");
		} finally {
			closeRepeat();
		}
	}

	private void closeRepeat() {

		try {
			if (bos != null)
				bos.close();
			if (br != null)
				br.close();
			if (fis != null)
				fis.close();

		} catch (IOException e) {
			System.out.println("입출력 오류 발생하였습니다.");
		}
	}

	public void whileRepeat(String path) {

		if (response != null) {
			try {
				fis = new FileInputStream(path);
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buf = new byte[1024];
				int length = 0;
				while ((length = fis.read(buf)) != -1) {
					bos.write(buf, 0, length);
				}

			} catch (FileNotFoundException e) {
				System.out.println("파일을 찾지 못하였습니다.");
			} catch (IOException e) {
				System.out.println("입출력 오류가 발생하였습니다.");
				e.printStackTrace();
			} finally {
				closeRepeat();
			}
		} else {
			System.out.println("response 객체가 유효하지 않습니다.");
		}
	}
}
