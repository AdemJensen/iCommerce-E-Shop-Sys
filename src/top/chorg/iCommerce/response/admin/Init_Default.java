package top.chorg.iCommerce.response.admin;

import top.chorg.iCommerce.api.Config;
import top.chorg.iCommerce.database.Database;
import top.chorg.iCommerce.function.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;

public class Init_Default extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String ip = request.getRemoteAddr();
//        if(!ip.equals("127.0.0.1") && !ip.equals("0:0:0:0:0:0:0:1")){
//            Request.becomeInfoPage(request, response, "error", "错误", "您的网络环境不符合要求",
//                    "由于安全策略，本页面只能在内网环境下才可以访问。");
//            return;
//        }
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String res_type;
        String res_str;
        String res_hint = "";
        try {
            Config conf = new Config();
            conf.SYSTEM_NAME = request.getParameter("SYSTEM_NAME");
            conf.USE_MODIFIED_INDEX = request.getParameter("USE_MODIFIED_INDEX").equals("true");
            conf.MODIFIED_INDEX_FILE = request.getParameter("MODIFIED_INDEX_FILE");

            conf.CHAT_SYSTEM = request.getParameter("CHAT_SYSTEM");

            conf.DATABASE_ADDR = request.getParameter("DATABASE_ADDR");
            conf.DATABASE_PORT = Integer.parseInt(request.getParameter("DATABASE_PORT"));
            conf.DATABASE_USERNAME = request.getParameter("DATABASE_USERNAME");
            conf.DATABASE_PASSWORD = request.getParameter("DATABASE_PASSWORD");
            String DATABASE_SCHEMA = request.getParameter("DATABASE_SCHEMA");
            conf.DATABASE_SCHEMA = "";
            conf.DATABASE_USE_UNICODE = request.getParameter("DATABASE_USE_UNICODE").equals("true");
            conf.DATABASE_ENCODING = request.getParameter("DATABASE_ENCODING");
            conf.DATABASE_TIMEZONE = request.getParameter("DATABASE_TIMEZONE");
            conf.DATABASE_USE_SSL = request.getParameter("DATABASE_USE_SSL").equals("true");
            conf.PUBLIC_KEY_RETRIEVAL = request.getParameter("PUBLIC_KEY_RETRIEVAL").equals("true");

            conf.DATABASE_TABLE_ADMIN = request.getParameter("DATABASE_TABLE_ADMIN");
            conf.DATABASE_TABLE_CART = request.getParameter("DATABASE_TABLE_CART");
            conf.DATABASE_TABLE_ITEM = request.getParameter("DATABASE_TABLE_ITEM");
            conf.DATABASE_TABLE_ITEM_TYPE = request.getParameter("DATABASE_TABLE_ITEM_TYPE");
            conf.DATABASE_TABLE_ITEM_TYPE_BELONG = request.getParameter("DATABASE_TABLE_ITEM_TYPE_BELONG");
            conf.DATABASE_TABLE_ORDER = request.getParameter("DATABASE_TABLE_ORDER");
            conf.DATABASE_TABLE_ORDER_BELONG = request.getParameter("DATABASE_TABLE_ORDER_BELONG");
            conf.DATABASE_TABLE_SHIPPING = request.getParameter("DATABASE_TABLE_SHIPPING");
            conf.DATABASE_TABLE_USER = request.getParameter("DATABASE_TABLE_USER");

            res_type = "success";
            res_str = "设置成功";

            if (conf.USE_MODIFIED_INDEX) {
                if (!new File(Config.getCustomFileAbsolutePath(request, conf.MODIFIED_INDEX_FILE)).exists()) {
                    res_type = "danger";
                    res_str = "设置失败";
                    res_hint += "[严重] 无法找到文件custom/" + conf.MODIFIED_INDEX_FILE + "<br/>";
                }
            }

            if (conf.DATABASE_PORT < 1024) {
                if (!res_type.equals("danger")) res_type = "warning";
                res_hint += "[警告] 0 - 1023端口为系统保留端口，不推荐作为数据库访问端口<br/>";
            }

            try {
                Database database = Database.connect(conf);
                PreparedStatement state = database.database.prepareStatement("CREATE DATABASE IF NOT EXISTS `iCommerce2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;");
                state.executeUpdate();
                state = database.database.prepareStatement("USE `iCommerce2`;");
                state.executeUpdate();
                // Table structure for table `admin`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `admin`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `admin` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `username` varchar(45) NOT NULL,\n" +
                        "  `password` varchar(45) NOT NULL,\n" +
                        "  `email` varchar(45) NOT NULL,\n" +
                        "  `level` int(11) NOT NULL DEFAULT '0' COMMENT '0 = edit items\\\\n1 = edit system conf',\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                 // Dumping data for table `admin`
                state = database.database.prepareStatement("LOCK TABLES `admin` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("/*!40000 ALTER TABLE `admin` DISABLE KEYS */;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `admin` VALUES (1,'root','e10adc3949ba59abbe56e057f20f883e','root@root.com',3);");
                state.executeUpdate();
                state = database.database.prepareStatement("/*!40000 ALTER TABLE `admin` ENABLE KEYS */;");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();
                 // -- Table structure for table `cart`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `cart`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `cart` (\n" +
                        "  `userId` int(11) NOT NULL,\n" +
                        "  `itemId` int(11) NOT NULL,\n" +
                        "  `quantity` int(11) NOT NULL DEFAULT '0',\n" +
                        "  PRIMARY KEY (`userId`,`itemId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                 // Table structure for table `item`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `item`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `item` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` varchar(45) NOT NULL,\n" +
                        "  `detail_page` varchar(45) NOT NULL,\n" +
                        "  `price_old` double NOT NULL,\n" +
                        "  `price_new` double NOT NULL,\n" +
                        "  `cover` int(11) NOT NULL DEFAULT '0',\n" +
                        "  `quantity` int(11) NOT NULL DEFAULT '0',\n" +
                        "  `deals` int(11) NOT NULL DEFAULT '0',\n" +
                        "  `releaseDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                // Table structure for table `item_type`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `item_type`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `item_type` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `index` int(11) NOT NULL DEFAULT '1',\n" +
                        "  `type_name` varchar(45) NOT NULL,\n" +
                        "  `type_comment` text NOT NULL,\n" +
                        "  `cover` int(11) NOT NULL DEFAULT '0',\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`),\n" +
                        "  UNIQUE KEY `type_name_UNIQUE` (`type_name`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                 // Table structure for table `item_type_belong`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `item_type_belong`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `item_type_belong` (\n" +
                        "  `itemId` int(11) NOT NULL,\n" +
                        "  `typeId` int(11) NOT NULL,\n" +
                        "  PRIMARY KEY (`itemId`,`typeId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                // Table structure for table `order`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `order`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `order` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `orderNum` varchar(45) NOT NULL DEFAULT '',\n" +
                        "  `orderTime` datetime NOT NULL,\n" +
                        "  `userId` int(11) NOT NULL,\n" +
                        "  `shippingId` int(11) NOT NULL,\n" +
                        "  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 = waiting for payment\\n1 = waiting for send\\n2 = shipping in process\\n3 = complete\\n4 = Refund request\\n 5 = Refund success\\n 6 = closed',\n" +
                        "  `expressNum` varchar(45) NOT NULL DEFAULT '',\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `orderId_UNIQUE` (`id`),\n" +
                        "  UNIQUE KEY `orderNum_UNIQUE` (`orderNum`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                 // Table structure for table `order_belong`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `order_belong`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `order_belong` (\n" +
                        "  `orderId` int(11) NOT NULL,\n" +
                        "  `productId` varchar(45) NOT NULL,\n" +
                        "  `amount` int(11) NOT NULL DEFAULT '1',\n" +
                        "  `price` varchar(45) NOT NULL,\n" +
                        "  `history_page` varchar(45) NOT NULL,\n" +
                        "  `history_cover` int(11) NOT NULL DEFAULT '0',\n" +
                        "  `history_name` varchar(45) NOT NULL,\n" +
                        "  PRIMARY KEY (`orderId`,`productId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                 // Table structure for table `shipping`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `shipping`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `shipping` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` varchar(20) NOT NULL,\n" +
                        "  `phone` varchar(20) NOT NULL,\n" +
                        "  `address` text NOT NULL,\n" +
                        "  `mailCode` varchar(20) NOT NULL,\n" +
                        "  `userId` int(11) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                 // Table structure for table `user`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `user`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `user` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `username` varchar(45) NOT NULL,\n" +
                        "  `password` varchar(45) NOT NULL,\n" +
                        "  `email` varchar(45) NOT NULL,\n" +
                        "  `balance` double NOT NULL DEFAULT '0',\n" +
                        "  `registerTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "  `disabled` tinyint(4) NOT NULL DEFAULT '0',\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `user_id_uindex` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
                state.executeUpdate();
                conf.DATABASE_SCHEMA = DATABASE_SCHEMA;
                database.database.close();
            } catch (Exception e) {
                e.printStackTrace();
                res_type = "danger";
                res_str = "设置失败";
                res_hint += "[严重] 连接或操作数据库时出现错误(" + e.getMessage() + ")" + "<br/>";
            }

            if (!res_type.equals("danger")) {
                conf.saveToFile(this.getServletContext().getRealPath("config.json"));
                if (!res_type.equals("warning")) res_hint = "所有设置均已成功应用";
            }

        } catch (Exception e) {
            res_type = "error";
            res_str = "设置失败";
            res_hint += "[严重] 应用设置时出现错误，原因：" + e.getMessage();
        }
        Request.becomeInfoPage(request, response, res_type, "系统设置", res_str, res_hint);
    }
}
