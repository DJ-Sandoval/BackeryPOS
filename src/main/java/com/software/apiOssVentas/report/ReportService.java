package com.software.apiOssVentas.report;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import com.software.apiOssVentas.model.Sale;
import com.software.apiOssVentas.service.SaleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.time.LocalDate;

@Service
public class ReportService {
    private static final String BACKGROUND_IMAGE_PATH = "static/cuerno.png";
    private static final BaseColor TABLE_COLOR = new BaseColor(139, 69, 19);
    private static final BaseColor HEADER_COLOR = new BaseColor(101, 67, 33);
    private static final BaseColor TEXT_COLOR = BaseColor.WHITE;
    private static final BaseColor AZUL_OSCURO = new BaseColor(0, 0, 0); // #2E3A8C en RGB
    private static final BaseColor HEADER_TEXT_COLOR = BaseColor.WHITE;
    private static final BaseColor CELL_TEXT_COLOR = new BaseColor(60, 47, 47);


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private SaleService saleService;

    public void generateDailyReport(LocalDate date, HttpServletResponse response) throws IOException, DocumentException {
        List<Sale> sales = saleService.getDailySales(date);
        generateReport(sales, "Reporte de Ventas Diarias - " + date, response);
    }

    public void generateWeeklyReport(LocalDate date, HttpServletResponse response) throws IOException, DocumentException {
        List<Sale> sales = saleService.getWeeklySales(date);
        generateReport(sales, "Reporte de Ventas Semanales - Semana del " + date, response);
    }

    public void generateMonthlyReport(int year, int month, HttpServletResponse response) throws IOException, DocumentException {
        List<Sale> sales = saleService.getMonthlySales(year, month);
        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
        generateReport(sales, "Reporte de Ventas Mensuales - " + monthName + " " + year, response);
    }

    public void generateGeneralReport(HttpServletResponse response) throws IOException, DocumentException {
        List<Sale> sales = saleService.listSale();
        generateReport(sales, "Reporte General de Ventas", response);
    }

    private void generateReport(List<Sale> sales, String title, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=reporte_ventas.pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Fondo (como marca de agua)
        addBackground(document, writer);

        // Título
        Paragraph titulo = new Paragraph(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, AZUL_OSCURO));
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        if (sales.isEmpty()) {
            Paragraph sinDatos = new Paragraph("No hay datos de ventas para mostrar", FontFactory.getFont(FontFactory.HELVETICA, 12, AZUL_OSCURO));
            sinDatos.setAlignment(Element.ALIGN_CENTER);
            document.add(sinDatos);
        } else {
            PdfPTable table = createSalesTable(sales);
            document.add(table);

            double total = sales.stream().mapToDouble(Sale::getTotal).sum();
            Paragraph totalParagraph = new Paragraph(String.format("Total de Ventas: $%,.2f", total),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, AZUL_OSCURO));
            totalParagraph.setAlignment(Element.ALIGN_RIGHT);
            totalParagraph.setSpacingBefore(20);
            document.add(totalParagraph);
        }

        document.close();
    }

    private PdfPTable createSalesTable(List<Sale> sales) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        addTableHeader(table, "Fecha");
        addTableHeader(table, "Hora");
        addTableHeader(table, "Día");
        addTableHeader(table, "Servicio");
        addTableHeader(table, "Total");

        for (Sale sale : sales) {
            addTableCell(table, sale.getFecha().toString());
            addTableCell(table, sale.getHora().toString());
            addTableCell(table, sale.getDia());
            addTableCell(table, sale.getServicio());
            addTableCell(table, String.format("$%,.2f", sale.getTotal()));
        }

        return table;
    }

    private void addTableHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, HEADER_TEXT_COLOR)));
        cell.setBackgroundColor(HEADER_COLOR);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addTableCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                FontFactory.getFont(FontFactory.HELVETICA, 11, CELL_TEXT_COLOR)));
        cell.setBackgroundColor(TABLE_COLOR);
        cell.setPadding(5);
        table.addCell(cell);
    }


    private void addBackground(Document document, PdfWriter writer) throws IOException, DocumentException {
        Resource resource = resourceLoader.getResource("classpath:" + BACKGROUND_IMAGE_PATH);
        if (resource.exists()) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            Image background = Image.getInstance(resource.getURL());

            float imgWidth = 50;  // Puedes ajustar según el tamaño deseado
            float imgHeight = 50;
            background.scaleAbsolute(imgWidth, imgHeight);

            float pageWidth = PageSize.A4.getWidth();
            float pageHeight = PageSize.A4.getHeight();

            for (float y = 0; y < pageHeight; y += imgHeight) {
                for (float x = 0; x < pageWidth; x += imgWidth) {
                    background.setAbsolutePosition(x, y);
                    canvas.addImage(background);
                }
            }
        }
    }



}