package util;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ToPdfConverter {
    private static final String INPUT_PATH = "C:\\Users\\Владелец\\IdeaProjects\\Bileton\\src\\main\\webapp\\ticketsPdf";//TODO: make it relative
    private static final String OUTPUT_PATH = "C:\\Users\\Владелец\\IdeaProjects\\Bileton\\src\\main\\webapp\\ticketsPdf";
    private static final String FONT = "C:\\WINDOWS\\Fonts\\ARIALUNI.TTF";//TODO: make it relative

    private ToPdfConverter() {
    }

    public static void createPdf(List<String> inputFiles, String outputId) {
        String output = outputId+".pdf";
        try (OutputStream os = new FileOutputStream(new File(OUTPUT_PATH, output))) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont(
                    FONT,
                    BaseFont.IDENTITY_H,
                    BaseFont.NOT_EMBEDDED
            );
            renderer.setDocument(new File(INPUT_PATH, inputFiles.get(0)));
            renderer.layout();
            renderer.createPDF(os, false);
            for (int i = 1; i < inputFiles.size(); i++)    {
                renderer.setDocument(new File(INPUT_PATH, inputFiles.get(i)));
                renderer.layout();
                renderer.writeNextDocument();
            }

            renderer.finishPDF();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
