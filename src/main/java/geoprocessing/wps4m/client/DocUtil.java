package geoprocessing.wps4m.client;

import org.apache.xmlbeans.XmlException;

import net.opengis.wps.x20.DataDocument.Data;
import net.opengis.wps.x20.DataInputType;
import net.opengis.wps.x20.LiteralValueDocument;
import net.opengis.wps.x20.LiteralValueDocument.LiteralValue;
import net.opengis.wps.x20.ReferenceType;
import net.opengis.wps.x20.impl.LiteralValueDocumentImpl.LiteralValueImpl;

public class DocUtil {
	public static DataInputType inputByName(String name,DataInputType[] inputTypes) {
		for(DataInputType inputType:inputTypes) {
			if(inputType.getId().equals(name))
				return inputType;
		}
		return null;
	}
	
	public static LiteralValue literalValue(String value) {
		//LiteralValue literalValue = LiteralValueDocument.Factory.newInstance().getLiteralValue();
		LiteralValueImpl literalvalue = new LiteralValueImpl(null);
		literalvalue.setStringValue(value);
		return literalvalue;
	}
	
	public static void SetLiteralValue(DataInputType input, String value) {
		Data data = input.getData();
		
		LiteralValueDocument literalDoc = null;
		try {
			literalDoc = LiteralValueDocument.Factory.parse(data.xmlText());
		} catch (XmlException e) {
			e.printStackTrace();
		}
		literalDoc.getLiteralValue().setStringValue(value);
		data.set(literalDoc);
	}
	
	public static void SetReferenceValue(DataInputType input, String value) {
		ReferenceType refType = input.getReference();
		refType.setHref(value);
	}
}
