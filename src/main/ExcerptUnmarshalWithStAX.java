package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import elements.fw.CommonTag;

/**
 * StAXとJAXBを利用し、部分的にアンマーシャルする。
 * @author W.Ryozo
 */
public class ExcerptUnmarshalWithStAX {

	/** マーシャル対象のXML */
	private static final File INPUT_FILE = new File("in/in.xml");

	/** マーシャル対象のタグクラス */
	private static final Class<?> TARGET_TAG_CLASS = CommonTag.class;

	public static void main(String[] args) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		UnmarshallerWrapper unmashallWrapper = new UnmarshallerWrapper(CommonTag.class);

		// TODO XMLEventReaderへの変更
		XMLStreamReader reader = null;
		BufferedInputStream stream = null;

		try {
			stream = new BufferedInputStream(new FileInputStream(INPUT_FILE));
			reader = factory.createXMLStreamReader(stream);

			// TODO フィルタの適用。
			// StartElementだけフィルタ
//			StreamFilter filter = new StreamFilter() {
//				@Override
//				public boolean accept(XMLStreamReader reader) {
//					return reader.isStartElement();
//				}
//			};
//			reader = factory.createFilteredReader(reader, filter);

			while (reader.hasNext()) {
				int eventType = reader.next();

				if (eventType == XMLStreamReader.START_ELEMENT) {
					if (isTargetPoint(reader)) {
						CommonTag tag = unmashallWrapper.unmarshal(reader);
						System.out.println(tag);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}

			if (reader != null) {
				try {
					reader.close();
				} catch (XMLStreamException xse) {
					xse.printStackTrace();
				}
			}
		}
	}

	/**
	 * 現在の読み込み位置がアンマーシャル対象のポイントであるか判定する。
	 * @param element 判定対象のタグ
	 * @return 判定結果
	 */
	public static boolean isTargetPoint(XMLStreamReader reader) throws Exception{
		if (!TARGET_TAG_CLASS.isAnnotationPresent(XmlRootElement.class)) {
			throw new RuntimeException("xmlrootelement annotation is required");
		}
		XmlRootElement elementAnnotation = TARGET_TAG_CLASS.getAnnotation(XmlRootElement.class);
		return elementAnnotation.name().equals(reader.getLocalName());
	}
}

/**
 * {@link Unmarshaller}のWrapperクラス。<br />
 * Unmarshalを担当する。
 * @author W.Ryozo
 * @param <T> アンマーシャル対象のタグクラス
 */
class UnmarshallerWrapper {
	private Unmarshaller unmarshaller;
	public UnmarshallerWrapper(Class<?>... targetClasses) {
		try {
			JAXBContext context = JAXBContext.newInstance(targetClasses);
			unmarshaller = context.createUnmarshaller();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public <T> T unmarshal(XMLStreamReader reader) throws JAXBException {
		if(reader == null) {
			throw new NullPointerException("reader is null");
		}
		// TODO 修正
		return (T) unmarshaller.unmarshal(reader);
	}
}