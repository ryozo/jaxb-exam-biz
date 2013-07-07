package main;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import elements.biz.A101BizTag;
import elements.fw.RootTag;

/**
 * Unmarshall用のテスト実行クラス
 * @author W.Ryozo
 */
public class Unmarshal {

	private static final File INFILE = new File("in/in.xml");

	public static void main(String[] args) throws Exception {
		JAXBContext context = JAXBContext.newInstance(RootTag.class,A101BizTag.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		RootTag tag = (RootTag) unmarshaller.unmarshal(INFILE);
		System.out.println(tag);

		System.out.println("pattern 2 ----------------");

//		JAXBContext context2 = JAXBContext.newInstance(CommonTag.class,A101BizTag.class);
//		Unmarshaller comUnmarshaller = context2.createUnmarshaller();
//
//		RootTag commonTag = (RootTag) comUnmarshaller.unmarshal(INFILE);
//		System.out.println(commonTag);

		System.out.println("pattern 3 ----------------");
//		JAXBContext context3 = JAXBContext.newInstance(A101BizTag.class);
//		Unmarshaller bizUnmarshaller = context3.createUnmarshaller();
//		RootTag biztag = (RootTag) bizUnmarshaller.unmarshal(INFILE);
//		System.out.println(biztag);
	}

}
