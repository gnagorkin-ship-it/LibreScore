package org.librescore.pdf
import org.junit.jupiter.api.Assertions.*; import org.junit.jupiter.api.Test
class PdfContractTest { @Test fun rendererClassExists(){ assertEquals("PdfPageRenderer", PdfPageRenderer::class.simpleName) } }
