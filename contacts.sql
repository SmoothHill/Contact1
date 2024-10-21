/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100413
 Source Host           : localhost:3306
 Source Schema         : contacts

 Target Server Type    : MySQL
 Target Server Version : 100413
 File Encoding         : 65001

 Date: 21/10/2024 16:18:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for man
-- ----------------------------
DROP TABLE IF EXISTS `man`;
CREATE TABLE `man`  (
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `Phone` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`Name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of man
-- ----------------------------
INSERT INTO `man` VALUES ('', NULL, NULL);
INSERT INTO `man` VALUES ('Wang', '四川省成都市', 134);
INSERT INTO `man` VALUES ('Yao', '西藏拉萨', 123);
INSERT INTO `man` VALUES ('Zhang', '云南省昆明市', 142);
INSERT INTO `man` VALUES ('山', '黑龙江省哈尔滨市', 190);

SET FOREIGN_KEY_CHECKS = 1;
