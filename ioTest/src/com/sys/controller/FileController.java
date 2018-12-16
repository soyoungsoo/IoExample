package com.sys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys.common.FileImpl;
import com.sys.util.FileIO;

/**
 * Servlet implementation class aa
 */
@WebServlet("/")
public class FileController extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;
	private static FileIO f = null;

	public void setFile(FileIO f) {
		this.f = f;
	}

	public FileController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		String path = request.getServletPath();
		String dirImg = "/img.do";
		String dirTxt = "/txt.do";
		String dirDownImg = "/imgDown.do";
		String dirDownTxt = "/txtDown.do";
		String pathImg = "C:\\Users\\soso0\\Downloads\\캡처.PNG";
		String pathTxt = "C:\\Users\\soso0\\Desktop\\테스트.txt";

		//FileIO f = new FileImpl(response);
		setFile(new FileImpl(response));
		
		if (path.equals(dirImg)) {
			f.imgPrint(pathImg);
		} else if (path.equals(dirTxt)) {
			f.txtPrint(pathTxt);
		} else if (path.equals(dirDownImg)) {
			f.FileDown(pathImg, "이미지", "PNG");
		} else if (path.equals(dirDownTxt)) {
			f.FileDown(pathTxt, "다운", "txt");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
