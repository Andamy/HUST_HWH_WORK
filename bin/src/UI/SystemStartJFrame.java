package UI;

import subwaysystem.Station;
import test.Test1;
import test.Test2;
import test.Test3;
import test.Test4;
import test.Test5;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.List;

public class SystemStartJFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea resultAreaStations, resultAreaRoutes;
    private JTextField stationNameField, distanceField, startStationField, endStationField, routeChoiceField;
    private JComboBox<String> paymentComboBox;
    private Test1 test1 = new Test1();
    private Test2 test2 = new Test2();
    private Test3 test3 = new Test3();
    private Test4 test4 = new Test4();
    private Test5 test5 = new Test5();
    public SystemStartJFrame() {
        setTitle("武汉地铁系统");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initMainScreen();
        initStationQueryScreen();
        initRouteQueryScreen();

        add(mainPanel);
        cardLayout.show(mainPanel, "Main");
        setLocationRelativeTo(null); // Center the window
    }

    private void initMainScreen() {
        JPanel mainScreen = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("武汉地铁系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        mainScreen.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton stationQueryButton = new JButton("站点查询");
        JButton routeQueryButton = new JButton("线路查询");

        stationQueryButton.addActionListener(e -> cardLayout.show(mainPanel, "StationQuery"));
        routeQueryButton.addActionListener(e -> cardLayout.show(mainPanel, "RouteQuery"));

        buttonPanel.add(stationQueryButton);
        buttonPanel.add(routeQueryButton);

        mainScreen.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(mainScreen, "Main");

        titleLabel.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // 添加菜单
        JMenuBar menuBar = new JMenuBar();
        JMenu aboutMenu = new JMenu("关于");
        JMenuItem authorMenuItem = new JMenuItem("关于作者");
        authorMenuItem.addActionListener(e -> showAuthorInfo());
        aboutMenu.add(authorMenuItem);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);
    }

    private void initStationQueryScreen() {
        JPanel stationQueryScreen = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JLabel stationNameLabel = new JLabel("请输入站点名：");
        stationNameField = new JTextField();
        JLabel distanceLabel = new JLabel("请输入距离（km）：");
        distanceField = new JTextField();
        JButton nearbyStationsButton = new JButton("查询附近站点");
        JButton transferStationsButton = new JButton("查询所有中转站");
        JButton backButton = new JButton("返回");

        nearbyStationsButton.addActionListener(e -> queryNearbyStations());
        transferStationsButton.addActionListener(e -> queryTransferStations());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        inputPanel.add(stationNameLabel);
        inputPanel.add(stationNameField);
        inputPanel.add(distanceLabel);
        inputPanel.add(distanceField);
        inputPanel.add(nearbyStationsButton);
        inputPanel.add(transferStationsButton);
        inputPanel.add(backButton);

        stationQueryScreen.add(inputPanel, BorderLayout.WEST);

        resultAreaStations = new JTextArea();
        resultAreaStations.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultAreaStations);
        stationQueryScreen.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(stationQueryScreen, "StationQuery");
    }

    private void initRouteQueryScreen() {
        JPanel routeQueryScreen = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1));

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.Y_AXIS));
        JLabel startStationLabel = new JLabel("请输入起始站站点名：");
        startStationField = new JTextField();
        JLabel endStationLabel = new JLabel("请输入终点站站点名：");
        endStationField = new JTextField();
        topLeftPanel.add(startStationLabel);
        topLeftPanel.add(startStationField);
        topLeftPanel.add(endStationLabel);
        topLeftPanel.add(endStationField);

        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS));
        JButton allRoutesButton = new JButton("查询所有路径");
        JButton shortestRouteButton = new JButton("选择最短路径");
        JLabel routeChoiceLabel = new JLabel("选择线路");
        routeChoiceField = new JTextField();
        JButton confirmButton = new JButton("确定");
        JLabel paymentLabel = new JLabel("选择付款方式");
        paymentComboBox = new JComboBox<>(new String[]{"普通单程票", "武汉通", "日票"});
        JButton payButton = new JButton("支付");
        JButton backButton = new JButton("返回");

        allRoutesButton.addActionListener(e -> queryAllRoutes());
        shortestRouteButton.addActionListener(e -> queryShortestRoute());
        confirmButton.addActionListener(e -> confirmRouteChoice());
        payButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "支付完成"));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        topRightPanel.add(allRoutesButton);
        topRightPanel.add(shortestRouteButton);
        topRightPanel.add(routeChoiceLabel);
        topRightPanel.add(routeChoiceField);
        topRightPanel.add(confirmButton);
        topRightPanel.add(paymentLabel);
        topRightPanel.add(paymentComboBox);
        topRightPanel.add(payButton);
        topRightPanel.add(backButton);

        inputPanel.add(topLeftPanel);
        inputPanel.add(topRightPanel);

        routeQueryScreen.add(inputPanel, BorderLayout.NORTH);

        resultAreaRoutes = new JTextArea();
        resultAreaRoutes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultAreaRoutes);
        routeQueryScreen.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(routeQueryScreen, "RouteQuery");
        setVisible(true);
    }

    private void queryNearbyStations() {
        resultAreaStations.setText("");
        String stationName = stationNameField.getText();
        String distance = distanceField.getText();
        try {
            test1.readtxt1();
            test2.readtxt2();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Station startStation = test2.findStationByName(test2.getStationAndNext().keySet(), stationName);
        if (startStation == null || startStation.getName().isEmpty()) {
            resultAreaStations.setText("输入站点未找到！");
            return;
        }
        double distanceValue = 0;
        try {
            distanceValue = Double.parseDouble(distance);
        } catch (NumberFormatException e) {
            resultAreaStations.setText("距离格式错误！");
            e.printStackTrace();
        }
        if (distanceValue < 0){
            resultAreaStations.setText("距离应大于0！");
            return;
        }
        test2.dijkstra(test2.getStationAndNext(), startStation, distanceValue);
        resultAreaStations.append("查询结果：附近站点信息\n");
        for (Map.Entry<Station, Double> entry : test2.getDistanceMap().entrySet()) {
            String station = entry.getKey().getName();
            resultAreaStations.append("<" + station + ", ");
            StringBuilder sb = new StringBuilder();
            for (String line : test1.getTransforStationlist().get(station)) {
                sb.append(line).append("、");
            }
            sb.setLength(sb.length() - 1);
            sb.append(", ");
            sb.append(String.format("%.3f", entry.getValue()) + ">");
            String transfor = sb.toString();
            resultAreaStations.append(transfor + "\n");
        }
        test2.getStationAndNext().clear();
        test2.getDistanceMap().clear();
    }

    private void queryTransferStations() {
        resultAreaStations.setText("");
        try {
            test1.readtxt1();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultAreaStations.append("查询结果：所有中转站信息\n");
        for (String station:test1.getTransforStationlist().keySet()){
            if (test1.getTransforStationlist().get(station).size()>1){
                StringBuilder sb = new StringBuilder();
                sb.append("<").append(station).append(", <");
                for (String line : test1.getTransforStationlist().get(station)) {
                    sb.append(line).append("、");
                }
                sb.setLength(sb.length() - 1);
                sb.append(">>");
                String transfor = sb.toString();
                resultAreaStations.append(transfor + "\n");
            }
        }
    }

    private void queryAllRoutes() {  
        String startStation = startStationField.getText();  
        String endStation = endStationField.getText();  
        try {
        	test1.readtxt1();
            test2.readtxt2();
        }catch (IOException e) {
            e.printStackTrace();
        }
        // 验证起始和目的站点  
        Station sStation = test2.findStationByName(test2.getStationAndNext().keySet(), startStation);  
        Station eStation = test2.findStationByName(test2.getStationAndNext().keySet(), endStation);  
        
      
        if (sStation == null || sStation.getName().isEmpty()) {  
        	resultAreaRoutes.setText("起始站点未找到！");  
            return;  
        }  
        if (eStation == null || eStation.getName().isEmpty()) {  
        	resultAreaRoutes.setText("目的站点未找到！");  
            return;  
        }  
      
        try {  
        	
            // 调用scannerAndDFS进行深度优先搜索  
            test3.scannerAndDFS(startStation, endStation);  
      
            // 假设scannerAndDFS更新了test3的状态，我们可以从中获取所有路径  
            List<List<Station>> allPaths = test3.getAllPaths(); // 假设getAllPaths返回所有路径的列表  
      
            if (allPaths == null || allPaths.isEmpty()) {  
            	resultAreaRoutes.setText("没有找到从起始站点到目的站点的路径！");  
                return;  
            }  
      
            // 显示查询结果  
            resultAreaRoutes.setText("查询结果：所有路径信息\n");  
            int i = 1;  
            for (List<Station> path : allPaths) {  
                StringBuilder sb = new StringBuilder();  
                sb.append("线路").append(i).append("<");  
                for (Station station : path) {  
                    sb.append(station.getName()).append(" ");  
                }  
                sb.append(">").append("\n");  
                resultAreaRoutes.append(sb.toString());  
                i++;  
            }  
      
        } catch (IOException e) {  
            // 在生产环境中，你可能希望使用日志记录而不是直接打印堆栈跟踪  
            e.printStackTrace();  
            resultAreaRoutes.setText("发生IO错误：" + e.getMessage());  
        }  
    }

    private void queryShortestRoute() {
    	try {
        	test1.readtxt1();
            test2.readtxt2();
        }catch (IOException e) {
            e.printStackTrace();
        }
    	 String startStation = startStationField.getText();  
         String endStation = endStationField.getText(); 
         Station sStation = test2.findStationByName(test2.getStationAndNext().keySet(), startStation);  
         Station eStation = test2.findStationByName(test2.getStationAndNext().keySet(), endStation);
         if (sStation == null || sStation.getName().isEmpty()) {  
         	resultAreaRoutes.setText("起始站点未找到！");  
             return;  
         }  
         if (eStation == null || eStation.getName().isEmpty()) {  
         	resultAreaRoutes.setText("目的站点未找到！");  
             return;  
         }
         

         try {  
        	 String result = "查询结果：最短路径信息\n"; // Placeholder for actual result
             resultAreaRoutes.append(result);
             test4.scannerAndDFS(startStation, endStation);
             List<Station> ShortestPath = test4.getShortestPath();
             StringBuilder sb = new StringBuilder();  
             sb.append("最短线路").append("<"); 
             for (Station station : ShortestPath) {  
                  
                  
                sb.append(station.getName()).append(" ");  
             } 
                 sb.append(">").append("\n");  
                 resultAreaRoutes.append(sb.toString());  
                   
              
       
       
         } catch (IOException e) {  
             // 在生产环境中，你可能希望使用日志记录而不是直接打印堆栈跟踪  
             e.printStackTrace();  
             resultAreaRoutes.setText("发生IO错误：" + e.getMessage());  
         }
        // TODO: Implement the logic to query the shortest route
        
    }

    private void confirmRouteChoice() {
        String routeChoice = routeChoiceField.getText();
        // TODO: Implement the logic to confirm route choice based on routeChoice
        String result = "确认结果：选择线路信息\n"; // Placeholder for actual result
        resultAreaRoutes.append(result);
    }

    private void showAuthorInfo() {
        JOptionPane.showMessageDialog(this, "作者信息：\n姓名：黄伟豪\n学号：U202211629\n班级：工程管理2201班");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SystemStartJFrame wms = new SystemStartJFrame();
        });
    }
}