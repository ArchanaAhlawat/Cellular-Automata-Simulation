package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.GeneralCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;

// For 1st extension of Visualization
public class UIGraphUtils {
	
	String title;
	
	public UIGraphUtils(String title) {
		this.title = title;
	}

	public Chart getPopulationChart(GeneralCell[][] matrix) {
		Map<String, Integer> statesToCounts = getCellStateCounts(matrix);
		List<PieChart.Data> pieChartRawData = new ArrayList<>();
		for (String state : statesToCounts.keySet()) {
			pieChartRawData.add(new PieChart.Data(state, statesToCounts.get(state)));
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(pieChartRawData);
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle(title);
		return chart;
	}

	private Map<String, Integer> getCellStateCounts(GeneralCell[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			throw new IllegalArgumentException();
		}
		int rows = matrix.length;
		int cols = matrix[0].length;
		Map<String, Integer> statesToCounts = new HashMap<>();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				GeneralCell cell = matrix[row][col];
				if (statesToCounts.containsKey(cell.getState())) {
					statesToCounts.put(cell.getState(), statesToCounts.get(cell.getState()) + 1);
				} else {
					statesToCounts.put(cell.getState(), 1);
				}
			}
		}
		return statesToCounts;
	}

}
