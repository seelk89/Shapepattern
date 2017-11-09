/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapepattern.Logic;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 *
 * @author Jesper
 */
public class ShapePatternController implements Initializable
{

    private Label label;
    @FXML
    private Label labelSize;
    @FXML
    private TextField sizeField;
    @FXML
    private ListView<String> listShapes;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<String> dropdownShapes;
    @FXML
    private ComboBox<String> dropdownPattern;
    @FXML
    private Canvas canvas;
    @FXML
    private Label noShapeLabel;
    @FXML
    private Label noSizeLabel;
    @FXML
    private Button clearBtn;
    @FXML
    private Button paintbtn;

    int numberOfCircles = 0;
    int numberOfRec = 0;

    double gridX = 0;
    double gridY = 0;

    int numberOfShapes = 0;

    //Initializes and adds items to the dropdown menus
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        dropdownShapes.setItems(FXCollections.observableArrayList());
        dropdownShapes.getItems().add("Circle");
        dropdownShapes.getItems().add("Rectangle");

        dropdownPattern.setItems(FXCollections.observableArrayList());
        dropdownPattern.getItems().add("Grid");
        dropdownPattern.getItems().add("Cross");
        dropdownPattern.getItems().add("Random");

        listShapes.setItems(FXCollections.observableArrayList());
    }

    // Adds Shapes to the observableArrayList
    @FXML
    private void addBtn(ActionEvent event)
    {
        String shape = dropdownShapes.getValue();
        String size = sizeField.getText();
        noShapeLabel.setText(" ");
        noSizeLabel.setText(" ");

        //Adds a circle to the list of things to be drawn, with a number behind it equal to what is currently in the textfield for size
        if (shape != null && size.isEmpty() == false && shape.equals("Circle"))
        {
            listShapes.getItems().add(shape + ": " + size);
            numberOfCircles = numberOfCircles + 1;
        }

        //Adds a rectangle to the list of things to be drawn, with a number behind it equal to what is currently in the textfield for size
        if (shape != null && size.isEmpty() == false && shape.equals("Rectangle"))
        {
            listShapes.getItems().add(shape + ": " + size);
            numberOfRec = numberOfRec + 1;
        }

        //Gives and error message in case no shape for the object to be draw has been chosen
        if (shape == null)
        {
            noShapeLabel.setText("Please select the shape!");
        }

        //Gives and error message in case no size for the object to be draw has been chosen
        if (size.isEmpty())
        {
            noSizeLabel.setText("Please select the \rsize!");
        }
    }

    // Creates coordinates for X depending on pattern
    private double chosenPatterX()
    {
        String pattern = dropdownPattern.getValue();

        // Gets a random X coordinate
        if (pattern == "Random")
        {
            return randomX();
        }

        // Gives an X coordinate for all the objects in the list and puts them 20 units apart in a grid pattern 
        if (pattern == "Grid")
        {
            int a = numberOfCircles + numberOfRec;

            for (int i = 0; i < a; i++)
            {
                gridX = gridX + 20;
                if ((gridX % 300) == 0)
                {
                    gridY = gridY + 20;
                    gridX = gridX - 300;
                }
            }
            return gridX;
        }

        // Shoud have given coordinates for a cross
        if (pattern == "Cross")
        {
            int a = numberOfCircles + numberOfRec;

            for (int i = 0; i < a; i++)
            {
                gridX = gridX + 20;
            }
            if ((gridY % 300) == 0)
            {
                gridY = gridY + 20;
                gridX = gridX - 70;
            }
        }
        return gridX;
    }

    // Creates coordinates for Y depending on pattern
    private double chosenPatterY()
    {
        String pattern = dropdownPattern.getValue();
        
        // Gets a random Y coordinate
        if (pattern == "Random")
        {
            return randomY();
        }

        // Gives a Y coordinate for the grid pattern
        if (pattern == "Grid")
        {
            return gridY;
        }

        // Shoud have given coordinates for a cross
        if (pattern == "Cross")
        {
            return gridX;
        }
        return 0;
    }

    // Clears canvas and list
    @FXML
    private void clearBtn(ActionEvent event)
    {
        canvas.getGraphicsContext2D().clearRect(0, 0, 300, 300);
        listShapes.getItems().clear();
        numberOfCircles = 0;
        numberOfRec = 0;
        gridX = 0;
        gridY = 0;
    }

    // Draws a green circle
    private void drawOval(GraphicsContext gc)
    {
        int sizenumber = Integer.parseInt(sizeField.getText());
        gc.setFill(Color.GREEN);
        gc.fillOval(chosenPatterX(), chosenPatterY(), sizenumber, sizenumber);
    }

    // Draws a red rectangle
    private void drawRect(GraphicsContext gc)
    {
        int sizenumber = Integer.parseInt(sizeField.getText());
        gc.setFill(Color.RED);
        gc.fillRect(chosenPatterX(), chosenPatterY(), sizenumber, sizenumber);
    }

    // Gets Random X between 1 and 300, 300 being the width of the canvas
    private int randomX()
    {
        int x = (int) (Math.random() * 300);
        return x;
    }

    // Gets Random Y between 1 and 300, 300 being the width of the canvas
    private int randomY()
    {
        int y = (int) (Math.random() * 300);
        return y;
    }

    // Paints Shapes in the List, but gets the size from the size text field, not the list
    @FXML
    private void paintShapes(ActionEvent event)
    {
        gatherCircles();
        gatherRectangle();
    }

    // Draws Circle equal to the number of circles with the drawOval method
    private void gatherCircles()
    {
        for (int i = 0; i < numberOfCircles; i++)
        {
            if (numberOfCircles != 0 && listShapes.getItems().contains("Circle: " + sizeField.getText()))
            {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                drawOval(gc);
            }
        }
    }

    // Draws rectangles equal to the number of rectangles with the drawRect method
    private void gatherRectangle()
    {
        for (int i = 0; i < numberOfRec; i++)
        {
            if (numberOfRec != 0 && listShapes.getItems().contains("Rectangle: " + sizeField.getText()))
            {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                drawRect(gc);
            }
        }
    }
}
