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

public class Init extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String res_type;
        String res_str;
        String res_hint = "";
        try {
            Config conf = new Config();
            conf.SYSTEM_NAME = "Apple Store";
            conf.USE_MODIFIED_INDEX = true;
            conf.MODIFIED_INDEX_FILE = "index-page-20191222.jsp";

            conf.CHAT_SYSTEM = "[HIDDEN]";

            conf.DATABASE_ADDR = "[HIDDEN]";
            conf.DATABASE_PORT = 3306;
            conf.DATABASE_USERNAME = "[HIDDEN]";
            conf.DATABASE_PASSWORD = "[HIDDEN]";
            String DATABASE_SCHEMA = "[HIDDEN]";
            conf.DATABASE_SCHEMA = "";
            conf.DATABASE_USE_UNICODE = true;
            conf.DATABASE_ENCODING = "UTF-8";
            conf.DATABASE_TIMEZONE = "";
            conf.DATABASE_USE_SSL = false;
            conf.PUBLIC_KEY_RETRIEVAL = true;

            conf.DATABASE_TABLE_ADMIN = "admin";
            conf.DATABASE_TABLE_CART = "cart";
            conf.DATABASE_TABLE_ITEM = "item";
            conf.DATABASE_TABLE_ITEM_TYPE = "item_type";
            conf.DATABASE_TABLE_ITEM_TYPE_BELONG = "item_type_belong";
            conf.DATABASE_TABLE_ORDER = "order";
            conf.DATABASE_TABLE_ORDER_BELONG = "order_belong";
            conf.DATABASE_TABLE_SHIPPING = "shipping";
            conf.DATABASE_TABLE_USER = "user";

            res_type = "success";
            res_str = "设置成功";

            if (!new File(Config.getCustomFileAbsolutePath(request, conf.MODIFIED_INDEX_FILE)).exists()) {
                res_type = "danger";
                res_str = "设置失败";
                res_hint += "[严重] 无法找到文件custom/" + conf.MODIFIED_INDEX_FILE + "<br/>";
            }

            try {
                Database database = Database.connect(conf);
                PreparedStatement state;
                state = database.database.prepareStatement("USE `" + DATABASE_SCHEMA + "`;");
                state.executeUpdate();
                // Table structure for table `" + conf.DATABASE_TABLE_ADMIN + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_ADMIN + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_ADMIN + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `username` varchar(45) NOT NULL,\n" +
                        "  `password` varchar(45) NOT NULL,\n" +
                        "  `email` varchar(45) NOT NULL,\n" +
                        "  `level` int(11) NOT NULL DEFAULT '0' COMMENT '0 = edit items\\n1 = edit system conf',\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                // Dumping data for table `" + conf.DATABASE_TABLE_ADMIN + "`
                state = database.database.prepareStatement("LOCK TABLES `" + conf.DATABASE_TABLE_ADMIN + "` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `" + conf.DATABASE_TABLE_ADMIN + "` VALUES (1,'root','e10adc3949ba59abbe56e057f20f883e','root@root.com',3);");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();
                // -- Table structure for table `" + conf.DATABASE_TABLE_CART + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_CART + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_CART + "` (\n" +
                        "  `userId` int(11) NOT NULL,\n" +
                        "  `itemId` int(11) NOT NULL,\n" +
                        "  `quantity` int(11) NOT NULL DEFAULT '0',\n" +
                        "  PRIMARY KEY (`userId`,`itemId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                // Table structure for table `" + conf.DATABASE_TABLE_ITEM + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_ITEM + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_ITEM + "` (\n" +
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
                        ") ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                state = database.database.prepareStatement("LOCK TABLES `" + conf.DATABASE_TABLE_ITEM + "` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `" + conf.DATABASE_TABLE_ITEM + "` VALUES (10,'iPad Pro','ipad-Pro-20191220.jsp',11785,6331,10,261,0,'2018-10-20 21:13:43'),(11,'AirPods Pro','Airpods-Pro-20191223.jsp',2199,1999,11,4999,0,'2019-10-22 13:17:00'),(12,'Macbook Air','test.jsf',10399,10399,18,10000,0,'2019-07-22 14:18:36'),(13,'MacBook Pro 13英寸','test.jsf',11499,11499,17,9999,0,'2019-11-22 14:23:29'),(14,'MacBook Pro 16英寸','test.jsf',22199,22199,19,9997,1,'2019-11-22 14:25:36'),(15,'iMac','test.jsf',8390,8390,20,10000,0,'2019-03-22 14:47:57'),(16,'iMac Pro','test.jsf',38138,38138,21,9999,0,'2017-12-22 14:48:19'),(17,'Mac Pro','test.jsf',47999,47999,22,998,0,'2019-12-10 14:48:51'),(18,'Mac mini','test.jsf',6331,6331,23,10000,0,'2018-10-22 14:49:20'),(19,'Pro Display XDR','test.jsf',39999,39999,24,999,0,'2019-12-10 14:49:49'),(20,'iPad Air','test.jsf',3869,3869,26,10000,0,'2019-03-22 14:52:16'),(21,'iPad','test.jsf',2699,2699,27,10000,0,'2019-09-10 14:52:46'),(22,'iPad mini','test.jsf',2921,2921,40,10000,0,'2019-03-22 14:53:06'),(23,'Apple Pencil （适用于iPad Pro的第二代）','test.jsf',973,973,28,9999,0,'2018-10-22 14:53:58'),(24,'Apple Pencil （第一代）','test.jsf',722,722,29,10000,0,'2017-10-22 14:54:26'),(25,'AirPods（配无线充电盒）','test.jsf',1588,1588,30,10000,0,'2019-03-22 14:55:17'),(26,'AirPods（配充电盒）','test.jsf',1246,1246,31,10000,0,'2019-03-22 14:55:51'),(27,'iPhone 11 Pro','test.jsf',6199,6199,32,9999,0,'2019-09-10 14:56:15'),(28,'iPhone 11','test.jsf',3699,3699,33,10000,0,'2019-09-10 14:56:42'),(29,'iPhone X?','test.jsf',6499,3689,34,9997,0,'2018-09-10 14:57:12'),(30,'Apple Watch Series 5','test.jsf',3199,3199,35,9999,0,'2019-10-22 14:58:44'),(31,'Apple Watch Nike','test.jsf',3199,3199,36,10000,0,'2019-10-22 14:59:10'),(32,'Apple Watch Hermès','test.jsf',11199,11199,37,10000,0,'2019-10-22 14:59:57'),(33,'Apple Watch Edition','test.jsf',6299,6299,38,10000,0,'2019-10-22 15:01:05'),(34,'Apple Watch Series 3','test.jsf',3188,1499,39,10000,0,'2017-10-22 15:01:34'),(35,'iPhone 8','test.jsf',7126,3034,41,10000,0,'2017-09-22 15:03:38');");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();

                // Table structure for table `" + conf.DATABASE_TABLE_ITEM_TYPE + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_ITEM_TYPE + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_ITEM_TYPE + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `index` int(11) NOT NULL DEFAULT '1',\n" +
                        "  `type_name` varchar(45) NOT NULL,\n" +
                        "  `type_comment` text NOT NULL,\n" +
                        "  `cover` int(11) NOT NULL DEFAULT '0',\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`),\n" +
                        "  UNIQUE KEY `type_name_UNIQUE` (`type_name`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                state = database.database.prepareStatement("LOCK TABLES `" + conf.DATABASE_TABLE_ITEM_TYPE + "` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `" + conf.DATABASE_TABLE_ITEM_TYPE + "` VALUES (1,1,'Mac','探索所有Apple出品的Macintosh®️计算机',12),(2,1,'iPad','探索所有Apple出品的iPad®️平板电脑',13),(3,1,'iPhone','探索所有Apple出品的iPhone®️手机',14),(4,1,'Watch','探索所有Apple出品的iWatch®️系列智能手表产品',15),(5,1,'配件','探索所有适用于Apple产品的配件',16);");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();

                // Table structure for table `" + conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` (\n" +
                        "  `itemId` int(11) NOT NULL,\n" +
                        "  `typeId` int(11) NOT NULL,\n" +
                        "  PRIMARY KEY (`itemId`,`typeId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                state = database.database.prepareStatement("LOCK TABLES `" + conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `" + conf.DATABASE_TABLE_ITEM_TYPE_BELONG + "` VALUES (10,2),(11,1),(11,2),(11,3),(11,4),(11,5),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(19,5),(20,2),(21,2),(22,2),(23,2),(23,5),(24,2),(24,5),(25,1),(25,2),(25,3),(25,4),(25,5),(26,1),(26,2),(26,3),(26,4),(26,5),(27,3),(28,3),(29,3),(30,4),(31,4),(32,4),(33,4),(34,4),(35,3);");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();

                // Table structure for table `" + conf.DATABASE_TABLE_ORDER + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_ORDER + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_ORDER + "` (\n" +
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
                        ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                state = database.database.prepareStatement("LOCK TABLES `" + conf.DATABASE_TABLE_ORDER + "` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `" + conf.DATABASE_TABLE_ORDER + "` VALUES (1,'c4ca4238a0b923820dcc509a6f75849b','2019-12-21 23:16:11',1,2,3,'75320631767512'),(2,'c81e728d9d4c2f636f067f89cc14862c','2019-12-22 00:22:53',1,1,5,'23333333'),(3,'eccbc87e4b5ce2fe28308fd9f2a7baf3','2019-12-22 00:32:45',1,2,3,'75320631767512'),(4,'a87ff679a2f3e71d9181a67b7542122c','2019-12-22 03:18:16',1,2,3,'23333333adhusd'),(5,'e4da3b7fbbce2345d7772b0674a318d5','2019-12-22 17:15:27',1,2,1,''),(6,'1679091c5a880faf6fb5e6087eb1b2dc','2019-12-22 18:55:44',1,1,1,''),(7,'8f14e45fceea167a5a36dedd4bea2543','2019-12-23 13:38:51',1,1,3,'23333333adhusd');");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();

                // Table structure for table `" + conf.DATABASE_TABLE_ORDER_BELONG + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_ORDER_BELONG + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_ORDER_BELONG + "` (\n" +
                        "  `orderId` int(11) NOT NULL,\n" +
                        "  `productId` varchar(45) NOT NULL,\n" +
                        "  `amount` int(11) NOT NULL DEFAULT '1',\n" +
                        "  `price` varchar(45) NOT NULL,\n" +
                        "  `history_page` varchar(45) NOT NULL,\n" +
                        "  `history_cover` int(11) NOT NULL DEFAULT '0',\n" +
                        "  `history_name` varchar(45) NOT NULL,\n" +
                        "  PRIMARY KEY (`orderId`,`productId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                state = database.database.prepareStatement("LOCK TABLES `" + conf.DATABASE_TABLE_ORDER_BELONG + "` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `" + conf.DATABASE_TABLE_ORDER_BELONG + "` VALUES (1,'10',18,'6331.0','ipad-Pro-20191220.jsp',10,'iPad Pro'),(1,'9',1,'9.99','test.jsf',1,'这是一部非常非常厉害的商品它的名字非常的长'),(2,'10',100,'6331.0','ipad-Pro-20191220.jsp',10,'iPad Pro'),(2,'4',9,'9.99','test.jsf',0,'test'),(3,'10',1000,'6331.0','ipad-Pro-20191220.jsp',10,'iPad Pro'),(4,'4',10,'9.99','test.jsf',0,'test'),(5,'10',1,'6331.0','ipad-Pro-20191220.jsp',10,'iPad Pro'),(5,'11',1,'1999.0','Airpods-Pro-20191223.jsp',11,'AirPods Pro'),(5,'14',1,'22199.0','test.jsf',19,'MacBook Pro 16英寸'),(5,'16',1,'38138.0','test.jsf',21,'iMac Pro'),(5,'17',1,'47999.0','test.jsf',22,'Mac Pro'),(5,'19',1,'39999.0','test.jsf',24,'Pro Display XDR'),(5,'23',1,'973.0','test.jsf',28,'Apple Pencil （适用于iPad Pro的第二代）'),(5,'27',1,'6199.0','test.jsf',32,'iPhone 11 Pro'),(5,'29',3,'3689.0','test.jsf',34,'iPhone X?'),(5,'30',1,'3199.0','test.jsf',35,'Apple Watch Series 5'),(6,'13',1,'11499.0','test.jsf',17,'MacBook Pro 13英寸'),(6,'14',1,'22199.0','test.jsf',19,'MacBook Pro 16英寸'),(6,'17',1,'47999.0','test.jsf',22,'Mac Pro'),(7,'14',1,'22199.0','test.jsf',19,'MacBook Pro 16英寸');");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();

                // Table structure for table `" + conf.DATABASE_TABLE_SHIPPING + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_SHIPPING + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_SHIPPING + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` varchar(20) NOT NULL,\n" +
                        "  `phone` varchar(20) NOT NULL,\n" +
                        "  `address` text NOT NULL,\n" +
                        "  `mailCode` varchar(20) NOT NULL,\n" +
                        "  `userId` int(11) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                state = database.database.prepareStatement("LOCK TABLES `" + conf.DATABASE_TABLE_SHIPPING + "` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `" + conf.DATABASE_TABLE_SHIPPING + "` VALUES (1,'苏卡不列斯基','10023336666','上海市徐汇区宜山路700号C4栋6层','250000',1),(2,'苏卡不列斯基','10023336666','山东省 济南市 历城区 港沟街道 舜华路1500号 齐鲁软件学院','250000',1);");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();

                // Table structure for table `" + conf.DATABASE_TABLE_USER + "`
                state = database.database.prepareStatement("DROP TABLE IF EXISTS `" + conf.DATABASE_TABLE_USER + "`;");
                state.executeUpdate();
                state = database.database.prepareStatement("CREATE TABLE `" + conf.DATABASE_TABLE_USER + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `username` varchar(45) NOT NULL,\n" +
                        "  `password` varchar(45) NOT NULL,\n" +
                        "  `email` varchar(45) NOT NULL,\n" +
                        "  `balance` double NOT NULL DEFAULT '0',\n" +
                        "  `registerTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "  `disabled` tinyint(4) NOT NULL DEFAULT '0',\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `user_id_uindex` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;");
                state.executeUpdate();
                state = database.database.prepareStatement("LOCK TABLES `" + conf.DATABASE_TABLE_USER + "` WRITE;");
                state.executeUpdate();
                state = database.database.prepareStatement("INSERT INTO `" + conf.DATABASE_TABLE_USER + "` VALUES (1,'test','e10adc3949ba59abbe56e057f20f883e','rentenglong@163.com',993272933.11,'2019-12-18 01:15:01',0),(2,'test2','e10adc3949ba59abbe56e057f20f883e','alpha@chorg.top',50000,'2019-12-22 12:59:39',0);");
                state.executeUpdate();
                state = database.database.prepareStatement("UNLOCK TABLES;");
                state.executeUpdate();

                database.database.close();
                conf.DATABASE_SCHEMA = DATABASE_SCHEMA;
            } catch (Exception e) {
                e.printStackTrace();
                res_type = "danger";
                res_str = "设置失败";
                res_hint += "[严重] 连接或操作数据库时出现错误(" + e.getMessage() + ")" + "<br/>";
            }

            if (!res_type.equals("danger")) {
                conf.saveToFile(this.getServletContext().getRealPath("config.json"));
                res_hint = "所有设置均已成功应用";
            }

        } catch (Exception e) {
            res_type = "error";
            res_str = "设置失败";
            res_hint += "[严重] 应用设置时出现错误，原因：" + e.getMessage();
        }
        if (res_type.equals("success")) {
            request.getRequestDispatcher("/guide").forward(request, response);
        } else {
            Request.becomeInfoPage(request, response, res_type, "系统设置", res_str, res_hint);
        }
    }
}
