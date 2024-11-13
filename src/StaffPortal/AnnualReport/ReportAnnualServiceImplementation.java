/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.AnnualReport;

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
public class ReportAnnualServiceImplementation implements ReportAnnualService {
    
    ReportAnnualPanel rap;
    ReportAnnualDAO dao = new ReportAnnualDAOImplementation();
    
    public ReportAnnualServiceImplementation(ReportAnnualPanel rap){
       this.rap = rap;
       displayYearlyCharts();
    }
    
    @Override
    public void displayYearlyCharts() {
        int year = rap.jYearChooser1.getYear();
            Map<String, Map<String, Integer>> purposeCountMap = dao.displayCharts(year); // Now returns nested map
            Map<String, Integer> totalPurposeCountMap = new HashMap<>();
            
            
            System.out.println("Purpose Count Map: " + purposeCountMap);
            

            
            int totalVisits = purposeCountMap.values().stream()
                                             .flatMap(map -> map.values().stream())
                                             .mapToInt(Integer::intValue)
                                             .sum();
            System.out.println("Total Visits for Year: " + totalVisits);  // Debugging output

            // Clear previous content in jPanel1 before adding new charts
            rap.jPanel1.removeAll();
            rap.jPanel2.removeAll();
            rap.jPanel1.setPreferredSize(new Dimension(600, 300)); // Adjust as needed
            rap.jPanel2.setPreferredSize(new Dimension(700, 300));
            rap.jPanel1.setLayout(new GridLayout(1, 1)); // or any layout you need
            rap.jPanel2.setLayout(new GridLayout(1, 1));
//            rmp.jPanel1.setLayout(new GridLayout(1, 2)); // Use GridLayout for side-by-side charts

                        // Create JTextArea for displaying the purpose/college/count details
                        // Clear the JTextArea before adding new content
            rap.jTextArea1.setText("");  // Clear previous content

            purposeCountMap.forEach((college, statusCountMap) -> {
                statusCountMap.forEach((purpose, count) -> {
                    // Aggregate the counts for each purpose
                totalPurposeCountMap.merge(purpose, count, Integer::sum);
                });
            });
            
            // Append the visit summary report header and total visits
            rap.jTextArea1.append("Visit Summary Report\n");
            rap.jTextArea1.append("====================\n");
            rap.jTextArea1.append("Total Visits: " + totalVisits + "\n\n");

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
            rap.jTextArea1.append(detailsText.toString());

            
            // Generate the pie chart
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            totalPurposeCountMap.forEach(pieDataset::setValue);

            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Visit Purposes for " + year, pieDataset, true, true, false);
            PiePlot piePlot = (PiePlot) pieChart.getPlot();
            piePlot.setSectionPaint("Emergency", Color.RED);
            piePlot.setSectionPaint("Appointment", Color.BLUE);
            piePlot.setSectionPaint("Resources", Color.GREEN);
            ChartPanel pieChartPanel = new ChartPanel(pieChart);
            pieChartPanel.setPreferredSize(new Dimension(200, 200));
            rap.jPanel1.add(pieChartPanel);  // Add pie chart to jPanel1

            // Generate the bar chart
            DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
            totalPurposeCountMap.forEach((purpose, count) -> {
                barDataset.addValue(count, "Visits", purpose);
            });

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Visit Purposes for " + year, "Status", "Count", barDataset);
            barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.BLUE);
            barChart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.RED);
            barChart.getCategoryPlot().getRenderer().setSeriesPaint(2, Color.GREEN);
            ChartPanel barChartPanel = new ChartPanel(barChart);
            barChartPanel.setPreferredSize(new Dimension(800, 300));
            rap.jPanel2.add(barChartPanel);  // Add bar chart to jPanel1

            // Ensure the JPanel updates with the new charts and details
            rap.jPanel1.revalidate();
            rap.jPanel1.repaint();
            rap.jPanel2.revalidate();
            rap.jPanel2.repaint();
            
            rap.revalidate();
            rap.repaint();

    }

    @Override
    public void printToPDF() {

        try (PDDocument document = new PDDocument()) {
                int year = rap.jYearChooser1.getYear();
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Draw the JTextArea content
                String textContent = rap.jTextArea1.getText();
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
                BufferedImage originalChartImage1 = new BufferedImage(rap.jPanel1.getWidth(), rap.jPanel1.getHeight(), BufferedImage.TYPE_INT_RGB);
                rap.jPanel1.paint(originalChartImage1.getGraphics());

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
                BufferedImage originalChartImage2 = new BufferedImage(rap.jPanel2.getWidth(), rap.jPanel2.getHeight(), BufferedImage.TYPE_INT_RGB);
                rap.jPanel2.paint(originalChartImage2.getGraphics());

                // Create a new BufferedImage for the resized second image
                BufferedImage chartImage2 = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d2 = chartImage2.createGraphics();
                g2d2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d2.drawImage(originalChartImage2, 0, 0, desiredWidth, desiredHeight, null);
                g2d2.dispose(); // Dispose of the graphics context

                // Save the second resized image to the PDF
                File tempImageFile2 = new File("chartImage2.png");
                ImageIO.write(chartImage2, "png", tempImageFile2);
 
                
                contentStream.drawImage(PDImageXObject.createFromFile(tempImageFile2.getAbsolutePath(), document), 25, startYForImages - chartImage1.getHeight() - chartImage2.getHeight() - 20); // Extra padding between images

                contentStream.close();


                    String filePath = "C:\\Users\\riche\\OneDrive\\Documents\\NetBeansProjects\\ClinicWithRFIDTrial1\\Reports";
                    try (FileOutputStream out = new FileOutputStream(filePath + "Yearly_visit_summary_" + year + ".pdf")) {
                        document.save(out); 
                    } catch (IOException e) {
                        e.printStackTrace(); 
                    } finally {
                        document.close(); 
                        tempImageFile1.delete(); 
                        tempImageFile2.delete();
                    }

                    JOptionPane.showMessageDialog(null, "Document saved successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error saving document: " + e.getMessage());
                }     
        }
    }
    

