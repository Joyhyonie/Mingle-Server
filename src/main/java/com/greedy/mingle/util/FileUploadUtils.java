package com.greedy.mingle.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

	public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		/* uploadDir: 업로드 하고 싶은 경로 / fileName: 저장할 파일 이름 / multipartFile: 현재 파일의 정보가 담겨있는 객체 */
		
		Path uploadPath = Paths.get(uploadDir);
		
		/* 업로드 경로가 존재하지 않을 경우 경로를 먼저 생성 */
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		/* 파일명 rename */
		String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		
		/* 파일 save */
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(replaceFileName); // resolve() : 경로 + 파일명 합치기
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); // Files.copy() 
			/* inputStream: 파일을 읽어옴 / filePath: 파일 전체 경로 / StandardCopyOption.REPLACE_EXISTING: 만약 해당 경로 상에 이미 존재한다면 덮어쓰기 (무조건 저장시킴) */
		} catch (IOException e) {
			throw new IOException("파일을 저장하지 못 했어유👻 fileName : " + fileName);
		}
		
		return replaceFileName;
		
	}

	public static void deleteFile(String uploadDir, String fileName) throws IOException {
		
		Path uploadPath = Paths.get(uploadDir);
		Path filePath = uploadPath.resolve(fileName);
		
		try {
			Files.delete(filePath);
		} catch (IOException e) {
			throw new IOException("파일을 삭제하지 못 했어유👻 fileName : " + fileName);
		}
		
	}


	
}
