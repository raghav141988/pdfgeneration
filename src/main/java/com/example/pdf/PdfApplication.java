package com.example.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xhtmlrenderer.extend.FontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

@SpringBootApplication
public class PdfApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfApplication.class, args);
		try {
			new PdfApplication().generatePDF();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generatePDF() throws DocumentException, IOException {
		ITextRenderer renderer = new ITextRenderer();
		  String template ="<?xml version=\"1.0\" encoding=\"utf-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><style>\n" + 
					"        html, body { font-family: Source Sans Pro !important; }\n" + 
					"        </style>"+
					"<meta charset=\"UTF-8\"/>"
					+ "</head><body><p>helo test Ö, ö,传/傳  → Ü, ü, Ÿ, and ÿ </p></body></html>";
		OutputStream outputStream = new FileOutputStream("message.pdf");
		renderer.getFontResolver().addFont(
                 this.getClass().getResource("/fonts/SourceSansPro-Regular.ttf").getPath(),
                 BaseFont.IDENTITY_H,
                 BaseFont.NOT_EMBEDDED
         );

         // unicode truetype font supporting 5572 characters
         // this font supports almost all the special characters ranging
         // from U+25A0 to U+25FF ("Geometric Shapes")
		renderer.getFontResolver().addFont(
                 this.getClass().getResource("/fonts/DejaVuSans.ttf").getPath(),
                 BaseFont.IDENTITY_H,
                 BaseFont.NOT_EMBEDDED
         );
         
         
		URL resource= ClassLoader.getSystemResource("ARIALUNI.TTF");
				                  
		
		
		//renderer.getFontResolver().addFont("/Users/raghavendrabhatt/Documents/projects/ARIALUNI.TTF",BaseFont.IDENTITY_H, false);
		//renderer.getFontResolver().addFont(resource.getFile(),BaseFont.IDENTITY_H, true);
		renderer.setDocumentFromString(template);
		renderer.layout();	
		
		renderer.createPDF(outputStream);
		outputStream.close();
	}
}
