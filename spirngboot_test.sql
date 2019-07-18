/*
Navicat MySQL Data Transfer

Source Server         : 本地链接
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : spirngboot_test

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2019-07-18 16:09:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dept`
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '部门0', '0');
INSERT INTO `dept` VALUES ('2', '部门1', '0');
INSERT INTO `dept` VALUES ('3', '部门2', '0');
INSERT INTO `dept` VALUES ('4', '部门3', '0');
INSERT INTO `dept` VALUES ('5', '部门4', '0');
INSERT INTO `dept` VALUES ('6', '部门5', '0');
INSERT INTO `dept` VALUES ('7', '部门6', '0');
INSERT INTO `dept` VALUES ('8', '部门7', '1');
INSERT INTO `dept` VALUES ('9', '部门8', '1');
INSERT INTO `dept` VALUES ('10', '部门9', '1');
INSERT INTO `dept` VALUES ('11', '部门10', '4');
INSERT INTO `dept` VALUES ('12', '部门11', '2');
INSERT INTO `dept` VALUES ('13', '部门12', '5');
INSERT INTO `dept` VALUES ('14', '部门13', '5');
INSERT INTO `dept` VALUES ('15', '部门14', '8');
INSERT INTO `dept` VALUES ('16', '部门15', '2');
INSERT INTO `dept` VALUES ('17', '部门16', '4');
INSERT INTO `dept` VALUES ('18', '部门17', '6');
INSERT INTO `dept` VALUES ('19', '部门18', '12');
INSERT INTO `dept` VALUES ('20', '部门19', '4');
INSERT INTO `dept` VALUES ('21', '部门20', '4');
INSERT INTO `dept` VALUES ('22', '部门21', '5');
INSERT INTO `dept` VALUES ('23', '部门22', '2');
INSERT INTO `dept` VALUES ('24', '部门23', '5');
INSERT INTO `dept` VALUES ('25', '部门24', '9');
INSERT INTO `dept` VALUES ('26', '部门25', '3');
INSERT INTO `dept` VALUES ('27', '部门26', '7');
INSERT INTO `dept` VALUES ('28', '部门27', '8');
INSERT INTO `dept` VALUES ('29', '部门28', '7');
INSERT INTO `dept` VALUES ('30', '部门29', '9');
INSERT INTO `dept` VALUES ('31', '部门30', '5');
INSERT INTO `dept` VALUES ('32', '部门31', '6');
INSERT INTO `dept` VALUES ('33', '部门32', '4');
INSERT INTO `dept` VALUES ('34', '部门33', '8');
INSERT INTO `dept` VALUES ('35', '部门34', '9');
INSERT INTO `dept` VALUES ('36', '部门35', '4');
INSERT INTO `dept` VALUES ('37', '部门36', '7');
INSERT INTO `dept` VALUES ('38', '部门37', '6');
INSERT INTO `dept` VALUES ('39', '部门38', '7');
INSERT INTO `dept` VALUES ('40', '部门39', '4');
INSERT INTO `dept` VALUES ('41', '部门40', '3');
INSERT INTO `dept` VALUES ('42', '部门41', '3');
INSERT INTO `dept` VALUES ('43', '部门42', '12');
INSERT INTO `dept` VALUES ('44', '部门43', '2');
INSERT INTO `dept` VALUES ('45', '部门44', '34');
INSERT INTO `dept` VALUES ('46', '部门45', '5');
INSERT INTO `dept` VALUES ('47', '部门46', '9');
INSERT INTO `dept` VALUES ('48', '部门47', '4');
INSERT INTO `dept` VALUES ('49', '部门48', '8');
INSERT INTO `dept` VALUES ('50', '部门49', '9');
INSERT INTO `dept` VALUES ('51', '部门50', '12');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `u_name` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', '1', '1', '3');
INSERT INTO `user` VALUES ('2', '1', '2', '2', '5');
INSERT INTO `user` VALUES ('3', '李大嘴0', '43', '河海大道0号', '6');
INSERT INTO `user` VALUES ('4', '李大嘴1', '90', '河海大道1号', '8');
INSERT INTO `user` VALUES ('5', '李大嘴2', '50', '河海大道2号', '12');
INSERT INTO `user` VALUES ('6', '李大嘴3', '58', '河海大道3号', '12');
INSERT INTO `user` VALUES ('7', '李大嘴4', '30', '河海大道4号', '5');
INSERT INTO `user` VALUES ('8', '李大嘴5', '10', '河海大道5号', '12');
INSERT INTO `user` VALUES ('9', '李大嘴6', '17', '河海大道6号', '12');
INSERT INTO `user` VALUES ('10', '李大嘴7', '73', '河海大道7号', '6');
INSERT INTO `user` VALUES ('11', '李大嘴8', '30', '河海大道8号', '4');
INSERT INTO `user` VALUES ('12', '李大嘴9', '45', '河海大道9号', '2');
INSERT INTO `user` VALUES ('13', '李大嘴10', '48', '河海大道10号', '8');
INSERT INTO `user` VALUES ('14', '李大嘴11', '64', '河海大道11号', '7');
INSERT INTO `user` VALUES ('15', '李大嘴12', '60', '河海大道12号', '2');
INSERT INTO `user` VALUES ('16', '李大嘴13', '86', '河海大道13号', '1');
INSERT INTO `user` VALUES ('17', '李大嘴14', '10', '河海大道14号', '1');
INSERT INTO `user` VALUES ('18', '李大嘴15', '27', '河海大道15号', '9');
INSERT INTO `user` VALUES ('19', '李大嘴16', '30', '河海大道16号', '8');
INSERT INTO `user` VALUES ('20', '李大嘴17', '65', '河海大道17号', '2');
INSERT INTO `user` VALUES ('21', '李大嘴18', '2', '河海大道18号', '9');
INSERT INTO `user` VALUES ('22', '李大嘴19', '74', '河海大道19号', '4');
INSERT INTO `user` VALUES ('23', '李大嘴20', '73', '河海大道20号', '12');
INSERT INTO `user` VALUES ('24', '李大嘴21', '11', '河海大道21号', '1');
INSERT INTO `user` VALUES ('25', '李大嘴22', '43', '河海大道22号', '4');
INSERT INTO `user` VALUES ('26', '李大嘴23', '86', '河海大道23号', '12');
INSERT INTO `user` VALUES ('27', '李大嘴24', '25', '河海大道24号', '7');
INSERT INTO `user` VALUES ('28', '李大嘴25', '39', '河海大道25号', '4');
INSERT INTO `user` VALUES ('29', '李大嘴26', '4', '河海大道26号', '6');
INSERT INTO `user` VALUES ('30', '李大嘴27', '18', '河海大道27号', '7');
INSERT INTO `user` VALUES ('31', '李大嘴28', '22', '河海大道28号', '12');
INSERT INTO `user` VALUES ('32', '李大嘴29', '92', '河海大道29号', '4');
INSERT INTO `user` VALUES ('33', '李大嘴30', '82', '河海大道30号', '8');
INSERT INTO `user` VALUES ('34', '李大嘴31', '94', '河海大道31号', '7');
INSERT INTO `user` VALUES ('35', '李大嘴32', '17', '河海大道32号', '5');
INSERT INTO `user` VALUES ('36', '李大嘴33', '34', '河海大道33号', '8');
INSERT INTO `user` VALUES ('37', '李大嘴34', '60', '河海大道34号', '3');
INSERT INTO `user` VALUES ('38', '李大嘴35', '85', '河海大道35号', '6');
INSERT INTO `user` VALUES ('39', '李大嘴36', '33', '河海大道36号', '1');
INSERT INTO `user` VALUES ('40', '李大嘴37', '40', '河海大道37号', '8');
INSERT INTO `user` VALUES ('41', '李大嘴38', '42', '河海大道38号', '9');
INSERT INTO `user` VALUES ('42', '李大嘴39', '6', '河海大道39号', '8');
INSERT INTO `user` VALUES ('43', '李大嘴40', '79', '河海大道40号', '12');
INSERT INTO `user` VALUES ('44', '李大嘴41', '13', '河海大道41号', '2');
INSERT INTO `user` VALUES ('45', '李大嘴42', '43', '河海大道42号', '3');
INSERT INTO `user` VALUES ('46', '李大嘴43', '28', '河海大道43号', '12');
INSERT INTO `user` VALUES ('47', '李大嘴44', '45', '河海大道44号', '2');
INSERT INTO `user` VALUES ('48', '李大嘴45', '10', '河海大道45号', '7');
INSERT INTO `user` VALUES ('49', '李大嘴46', '78', '河海大道46号', '8');
INSERT INTO `user` VALUES ('50', '李大嘴47', '40', '河海大道47号', '8');
INSERT INTO `user` VALUES ('51', '李大嘴48', '94', '河海大道48号', '4');
INSERT INTO `user` VALUES ('52', '李大嘴49', '71', '河海大道49号', '4');
INSERT INTO `user` VALUES ('53', '李大嘴50', '43', '河海大道50号', '4');
