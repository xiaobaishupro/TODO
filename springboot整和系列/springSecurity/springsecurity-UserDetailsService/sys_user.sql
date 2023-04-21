/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : ruiji

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 21/04/2023 14:43:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '66', '2023-04-11 02:00:27', '2023-04-27 02:00:32', '$2a$10$onNtpOIhtMf.4IVyecahWOGD9/1phA/I1lT4LdU9lMQrUbTyyH3n2', NULL);
INSERT INTO `sys_user` VALUES (2, 'admin', '2023-04-20 10:44:17', NULL, '$2a$10$onNtpOIhtMf.4IVyecahWOGD9/1phA/I1lT4LdU9lMQrUbTyyH3n2', 'admin,ROLE_order');
INSERT INTO `sys_user` VALUES (3, 'user', '2023-04-18 14:31:50', NULL, '$10$onNtpOIhtMf.4IVyecahWOGD9/1phA/I1lT4LdU9lMQrUbTyyH3n2', 'user,ROLE_order');

SET FOREIGN_KEY_CHECKS = 1;
