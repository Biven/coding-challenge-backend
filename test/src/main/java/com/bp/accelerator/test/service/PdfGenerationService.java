package com.bp.accelerator.test.service;

import com.bp.accelerator.test.data.entity.Application;
import com.bp.accelerator.test.data.entity.PastProject;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfGenerationService {

    /**
     * Very broadly organized code, as I didn't work with itextpds previously
     */
    public ByteArrayResource generatePdf(Application application) throws DocumentException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(application.getName(), font);
        document.add(chunk);

        PdfPTable table = new PdfPTable(2);
        PdfPCell cell1 = new PdfPCell(new Phrase("Email"));
        PdfPCell cell2 = new PdfPCell(new Phrase(application.getEmail()));
        PdfPCell cell3 = new PdfPCell(new Phrase("Github User"));
        PdfPCell cell4 = new PdfPCell(new Phrase(application.getGithubUser()));
        PdfPCell cell5 = new PdfPCell(new Phrase("Avatar"));
        Image image = Image.getInstance(URI.create("https://github.com/" + application.getGithubUser() + ".png").toURL());
        image.scaleAbsolute(40, 40);
        PdfPCell cell6 = new PdfPCell(image);
        PdfPCell cell7 = new PdfPCell(new Phrase("Past Projects"));
        PdfPCell cell8 = new PdfPCell();

        addCellsToTable(table, List.of(cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8));

        for (PastProject pastProject : application.getPastProjects()) {
            List<PdfPCell> cells = new ArrayList<>();
            cells.add(new PdfPCell(new Phrase("Project Name")));
            cells.add(new PdfPCell(new Phrase(pastProject.getProjectName())));

            cells.add(new PdfPCell(new Phrase("Employment")));
            cells.add(new PdfPCell(new Phrase(pastProject.getEmployment().name())));

            cells.add(new PdfPCell(new Phrase("Capacity")));
            cells.add(new PdfPCell(new Phrase(pastProject.getCapacity().name())));

            cells.add(new PdfPCell(new Phrase("Duration")));
            cells.add(new PdfPCell(new Phrase(pastProject.getDuration().toString())));

            cells.add(new PdfPCell(new Phrase("Start Year")));
            cells.add(new PdfPCell(new Phrase(pastProject.getStartYear())));

            cells.add(new PdfPCell(new Phrase("Role")));
            cells.add(new PdfPCell(new Phrase(pastProject.getRole())));

            cells.add(new PdfPCell(new Phrase("Team Size")));
            cells.add(new PdfPCell(new Phrase(pastProject.getTeamSize())));

            if (StringUtils.isNotBlank(pastProject.getRepoLink())) {
                cells.add(new PdfPCell(new Phrase("Repository")));
                cells.add(new PdfPCell(new Phrase(pastProject.getRepoLink())));
            }

            if (StringUtils.isNotBlank(pastProject.getLiveUrl())) {
                cells.add(new PdfPCell(new Phrase("Live URL")));
                cells.add(new PdfPCell(new Phrase(pastProject.getLiveUrl())));
            }

            addCellsToTable(table, cells);
        }

        document.add(table);

        document.close();
        return new ByteArrayResource(outputStream.toByteArray());
    }

    private void addCellsToTable(PdfPTable table, List<PdfPCell> cells) {
        for (PdfPCell cell : cells) {
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
    }
}
