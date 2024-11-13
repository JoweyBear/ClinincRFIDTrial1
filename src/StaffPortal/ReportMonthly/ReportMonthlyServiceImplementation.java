/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.ReportMonthly;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author joena
 */
public class ReportMonthlyServiceImplementation implements ReportMonthlyService {
    
    ReportMonthlyPanel rmp;
    ReportMonthlyDAO dao = new ReportMonthlyDAOImplementation();
    
    public ReportMonthlyServiceImplementation(ReportMonthlyPanel rmp){
        this.rmp = rmp;
//       displayCharts();
        displayMonthlyCharts();
    }

    private void displayCharts() {
        Map<String, Integer> purposeCountMap = dao.displayCurrentMonth();
        int totalVisits = purposeCountMap.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total Visits for this month: " + totalVisits);  // Debugging output

        // Clear previous content in jPanel1 before adding new charts
        rmp.jPanel1.removeAll();
        rmp.jPanel2.removeAll();
        rmp.jPanel1.setPreferredSize(new Dimension(300, 300)); // Adjust as needed
        rmp.jPanel2.setPreferredSize(new Dimension(600, 300));
        rmp.jPanel1.setLayout(new GridLayout(1, 1)); // or any layout you need
        rmp.jPanel2.setLayout(new GridLayout(1, 1));
//        rmp.jPanel1.setLayout(new GridLayout(1, 2)); // Use GridLayout for side-by-side charts

        // Generate the pie chart
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : purposeCountMap.entrySet()) {
            String purpose = entry.getKey();
            int count = entry.getValue();
            pieDataset.setValue(purpose, count);
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Visit Purposes for This Month", pieDataset, true, true, false);
        PiePlot piePlot = (PiePlot) pieChart.getPlot(); // Cast to PiePlot
        piePlot.setSectionPaint("Emergency", Color.RED);
        piePlot.setSectionPaint("Appointment", Color.BLUE);
        piePlot.setSectionPaint("Resources", Color.GREEN);
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new Dimension(200, 200));
        rmp.jPanel1.add(pieChartPanel);  // Add pie chart to jPanel1

        // Generate the bar graph
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : purposeCountMap.entrySet()) {
            String purpose = entry.getKey();
            int count = entry.getValue();
            barDataset.addValue(count, "Visits", purpose);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Visit Purposes for This Month", "Purpose", "Count", barDataset);
        barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.BLUE); // First series color
        barChart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.RED);  // Second series color
        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new Dimension(800, 300));
        rmp.jPanel2.add(barChartPanel);  // Add bar chart to jPanel1

        // Ensure jPanel1 updates with the new charts
        rmp.jPanel1.revalidate();  // Revalidate to apply layout changes
        rmp.jPanel1.repaint(); 
        rmp.jPanel2.revalidate();  // Revalidate to apply layout changes
        rmp.jPanel2.repaint();
    }

    @Override
    public void displayMonthlyCharts() {
            int month = rmp.jMonthChooser1.getMonth() + 1;
            Map<String, Map<String, Integer>> purposeCountMap = dao.displayCharts(month); // Now returns nested map
            Map<String, Integer> totalPurposeCountMap = new HashMap<>();
            // Calculate total visits across all departments and purposes
            int totalVisits = purposeCountMap.values().stream()
                                             .flatMap(map -> map.values().stream())
                                             .mapToInt(Integer::intValue)
                                             .sum();
            System.out.println("Total Visits for this month: " + totalVisits);  // Debugging output

            // Clear previous content in jPanel1 before adding new charts
            rmp.jPanel1.removeAll();
            rmp.jPanel2.removeAll();
            rmp.jPanel1.setPreferredSize(new Dimension(600, 300)); // Adjust as needed
            rmp.jPanel2.setPreferredSize(new Dimension(700, 300));
            rmp.jPanel1.setLayout(new GridLayout(1, 1)); // or any layout you need
            rmp.jPanel2.setLayout(new GridLayout(1, 1));
//            rmp.jPanel1.setLayout(new GridLayout(1, 2)); // Use GridLayout for side-by-side charts

                        // Create JTextArea for displaying the purpose/college/count details
                        // Clear the JTextArea before adding new content
            rmp.jTextArea1.setText("");  // Clear previous content

            // Append the visit summary report header and total visits
            rmp.jTextArea1.append("Visit Summary Report\n");
            rmp.jTextArea1.append("====================\n");
            rmp.jTextArea1.append("Total Visits: " + totalVisits + "\n\n");

            StringBuilder detailsText = new StringBuilder();

            // Populate detailsText with formatted data
            purposeCountMap.forEach((college, statusCountMap) -> {
                statusCountMap.forEach((status, count) -> {
                    detailsText.append("Status: ").append(status)
                               .append(", College: ").append(college)
                               .append(", Count: ").append(count).append("\n\n");
                });
            });

            // Append detailsText to the JTextArea
            rmp.jTextArea1.append(detailsText.toString());
            
            purposeCountMap.forEach((college, statusCountMap) -> {
            statusCountMap.forEach((purpose, count) -> {
                    // Aggregate the counts for each purpose
                    totalPurposeCountMap.merge(purpose, count, Integer::sum);
                });
            });

            // Generate the pie chart
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            totalPurposeCountMap.forEach(pieDataset::setValue);

            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Visit Purposes for This Month", pieDataset, true, true, false);
            PiePlot piePlot = (PiePlot) pieChart.getPlot();
            piePlot.setSectionPaint("Emergency", Color.RED);
            piePlot.setSectionPaint("Appointment", Color.BLUE);
            piePlot.setSectionPaint("Resources", Color.GREEN);
            ChartPanel pieChartPanel = new ChartPanel(pieChart);
            pieChartPanel.setPreferredSize(new Dimension(200, 200));
            rmp.jPanel1.add(pieChartPanel);  // Add pie chart to jPanel1

            // Generate the bar chart
            DefaultCategoryDataset barDataset = new DefaultCategoryDataset();            
                totalPurposeCountMap.forEach((purpose, count) -> {
                    barDataset.addValue(count, "Visits", purpose);
                });

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Visit Purposes for This Month", "Status", "Count", barDataset);
            barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.BLUE);
            barChart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.RED);
            barChart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.GREEN);
            ChartPanel barChartPanel = new ChartPanel(barChart);
            barChartPanel.setPreferredSize(new Dimension(800, 300));
            rmp.jPanel2.add(barChartPanel);  // Add bar chart to jPanel1

            // Ensure the JPanel updates with the new charts and details
            rmp.jPanel1.revalidate();
            rmp.jPanel1.repaint();
            rmp.jPanel2.revalidate();
            rmp.jPanel2.repaint();
            
            rmp.revalidate();
            rmp.repaint();
        }

//        int month = rmp.jMonthChooser1.getMonth() + 1;
//        Map<String, Integer> purposeCountMap = dao.displayCharts(month);
//        int totalVisits = purposeCountMap.values().stream().mapToInt(Integer::intValue).sum();
//        System.out.println("Total Visits for this month: " + totalVisits);  // Debugging output
//
//        // Clear previous content in jPanel1 before adding new charts
//        rmp.jPanel1.removeAll();
//        rmp.jPanel1.setLayout(new GridLayout(1, 2)); // Use GridLayout for side-by-side charts
//
//        // Generate the pie chart
//        DefaultPieDataset pieDataset = new DefaultPieDataset();
//        for (Map.Entry<String, Integer> entry : purposeCountMap.entrySet()) {
//            String purpose = entry.getKey();
//            int count = entry.getValue();
//            pieDataset.setValue(purpose, count);
//        }
//
//        JFreeChart pieChart = ChartFactory.createPieChart(
//                "Visit Purposes for This Month", pieDataset, true, true, false);
//        PiePlot piePlot = (PiePlot) pieChart.getPlot(); // Cast to PiePlot
//        piePlot.setSectionPaint("Emergency", Color.RED);
//        piePlot.setSectionPaint("Appointment", Color.BLUE);
//        piePlot.setSectionPaint("Resources", Color.GREEN);
//        ChartPanel pieChartPanel = new ChartPanel(pieChart);
//        pieChartPanel.setPreferredSize(new Dimension(200, 200));
//        rmp.jPanel1.add(pieChartPanel);  // Add pie chart to jPanel1
//
//        // Generate the bar graph
//        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
//        for (Map.Entry<String, Integer> entry : purposeCountMap.entrySet()) {
//            String purpose = entry.getKey();
//            int count = entry.getValue();
//            barDataset.addValue(count, "Visits", purpose);
//        }
//
//        JFreeChart barChart = ChartFactory.createBarChart(
//                "Visit Purposes for This Month", "Purpose", "Count", barDataset);
//        barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.BLUE); // First series color
//        barChart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.RED);  // Second series color
//        ChartPanel barChartPanel = new ChartPanel(barChart);
//        barChartPanel.setPreferredSize(new Dimension(800, 300));
//        rmp.jPanel1.add(barChartPanel);  // Add bar chart to jPanel1
//
//        // Ensure jPanel1 updates with the new charts
//        rmp.jPanel1.revalidate();  // Revalidate to apply layout changes
//        rmp.jPanel1.repaint(); 

    @Override
    public void printToPDF() {
        
            try (PDDocument document = new PDDocument()) {
                int month = rmp.jMonthChooser1.getMonth() + 1;
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Draw the JTextArea content
                String textContent = rmp.jTextArea1.getText();
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(25, page.getMediaBox().getHeight() - 25); // Start from top-left corner

                // Prepare to measure text height
                Font font = new Font("Helvetica", Font.PLAIN, 12);
                FontMetrics metrics = new Canvas().getFontMetrics(font);
                int lineHeight = metrics.getHeight(); // Get the height of a single line

                int numberOfLines = textContent.split("\n").length;
                float textHeight = lineHeight * numberOfLines; // Total height of the text

                // Draw the text
                for (String line : textContent.split("\n")) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -lineHeight); // Move down for the next line
                }
                contentStream.endText();

                // Calculate the starting Y position for the images
                float startYForImages = page.getMediaBox().getHeight() - 25 - textHeight - 20; // Extra space below the text

                int desiredWidth = 500;
                int desiredHeight = 200;

                // Capture the first chart as an image and resize it
                BufferedImage originalChartImage1 = new BufferedImage(rmp.jPanel1.getWidth(), rmp.jPanel1.getHeight(), BufferedImage.TYPE_INT_RGB);
                rmp.jPanel1.paint(originalChartImage1.getGraphics());

                // Create a new BufferedImage for the resized image
                BufferedImage chartImage1 = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d1 = chartImage1.createGraphics();
                g2d1.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d1.drawImage(originalChartImage1, 0, 0, desiredWidth, desiredHeight, null);
                g2d1.dispose(); // Dispose of the graphics context

                // Save the first resized image to the PDF
                File tempImageFile1 = new File("chartImage1.png");
                ImageIO.write(chartImage1, "png", tempImageFile1);

                // Draw the resized first image on the PDF
                contentStream.drawImage(PDImageXObject.createFromFile(tempImageFile1.getAbsolutePath(), document), 25, startYForImages - chartImage1.getHeight());

                // Capture the second chart as an image and resize it
                BufferedImage originalChartImage2 = new BufferedImage(rmp.jPanel2.getWidth(), rmp.jPanel2.getHeight(), BufferedImage.TYPE_INT_RGB);
                rmp.jPanel2.paint(originalChartImage2.getGraphics());

                // Create a new BufferedImage for the resized second image
                BufferedImage chartImage2 = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d2 = chartImage2.createGraphics();
                g2d2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d2.drawImage(originalChartImage2, 0, 0, desiredWidth, desiredHeight, null);
                g2d2.dispose(); // Dispose of the graphics context

                // Save the second resized image to the PDF
                File tempImageFile2 = new File("chartImage2.png");
                ImageIO.write(chartImage2, "png", tempImageFile2);
                
                // Capture the first chart as an image and add it to the PDF
//                BufferedImage chartImage1 = new BufferedImage(rmp.jPanel1.getWidth(), rmp.jPanel1.getHeight(), BufferedImage.TYPE_INT_RGB);
//                rmp.jPanel1.paint(chartImage1.getGraphics());
//
//                // Save the first image to the PDF
//                File tempImageFile1 = new File("chartImage1.png");
//                ImageIO.write(chartImage1, "png", tempImageFile1);
//
//                // Draw the first image on the PDF
//                contentStream.drawImage(PDImageXObject.createFromFile(tempImageFile1.getAbsolutePath(), document), 25, startYForImages - chartImage1.getHeight());
//
//                // Capture the second chart as an image and add it to the PDF
//                BufferedImage chartImage2 = new BufferedImage(rmp.jPanel2.getWidth(), rmp.jPanel2.getHeight(), BufferedImage.TYPE_INT_RGB);
//                rmp.jPanel2.paint(chartImage2.getGraphics());
//
//                // Save the second image to the PDF
//                File tempImageFile2 = new File("chartImage2.png");
//                ImageIO.write(chartImage2, "png", tempImageFile2);

                // Draw the second image on the PDF
                contentStream.drawImage(PDImageXObject.createFromFile(tempImageFile2.getAbsolutePath(), document), 25, startYForImages - chartImage1.getHeight() - chartImage2.getHeight() - 20); // Extra padding between images

                contentStream.close();

                // Save the document
                String filePath = "C:\\Users\\riche\\OneDrive\\Documents\\NetBeansProjects\\ClinicWithRFIDTrial1\\Reports";
                try (FileOutputStream out = new FileOutputStream(filePath + "Monthly_visit_summary_" + month + ".pdf")) {
                    document.save(out); // Save the document to the specified path
                } catch (IOException e) {
                    e.printStackTrace(); // Handle exceptions
                } finally {
                    document.close(); // Ensure the document is closed
                    tempImageFile1.delete(); // Clean up the temporary image file
                    tempImageFile2.delete(); // Clean up the second temporary image file
                }

                JOptionPane.showMessageDialog(null, "Document saved successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error saving document: " + e.getMessage());
            }
                    }
    
    }

