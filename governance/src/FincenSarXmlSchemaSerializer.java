package com.financial.pipeline.governance;

import java.io.Serializable;

public class FincenSarXmlSchemaSerializer implements Serializable {
    private static final long serialVersionUID = 1L;
    public String generateSarHeader(String filingInstitutionId) {
        return "<SAR_BATCH version=\"2.0\" institution=\"" + filingInstitutionId + "\">";
    }
}
