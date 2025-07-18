/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 14/07/2025 11:30:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `title` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `category_id` int NOT NULL COMMENT '分类ID',
  `author_id` int NOT NULL COMMENT '作者ID',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：0-草稿，1-已发布',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_author_id`(`author_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, 'Spring boot 获取今年所有的节假日', '根据时间查看是否是节假日以及获取今年所有的节假日时间', 1, 2, 1);
INSERT INTO `article` VALUES (2, 'int(1) 和 int(10) 的区别！', '很多资深开发竟然都不清楚 int(1) 和 int(10) 的区别！', 3, 1, 1);

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` int NOT NULL COMMENT '文章ID',
  `tag_id` int NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id` ASC) USING BTREE,
  INDEX `idx_tag_id`(`tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章-标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_tag
-- ----------------------------
INSERT INTO `article_tag` VALUES (3, 2, 5);
INSERT INTO `article_tag` VALUES (4, 1, 2);
INSERT INTO `article_tag` VALUES (5, 1, 3);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '后端开发', '记录后端开发相关技术');
INSERT INTO `category` VALUES (2, '前端开发', '记录前端开发相关技术');
INSERT INTO `category` VALUES (3, '数据库', '记录数据库相关技术');
INSERT INTO `category` VALUES (4, '生活随笔', '记录生活中的美好');
INSERT INTO `category` VALUES (5, '博客文章', '写在博客里的文章');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `article_id` int NOT NULL COMMENT '文章ID',
  `user_id` int NOT NULL COMMENT '评论用户ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `parent_id` int NULL DEFAULT NULL COMMENT '父评论ID（回复评论时使用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_comment_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '前端路由路径',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '前端组件路径',
  `parent_id` int NULL DEFAULT 0 COMMENT '父菜单ID（0为根）',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏 0-显示 1-隐藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '用户管理', 'User', '/user', '@/views/user/index', 0, 1, 0);
INSERT INTO `menu` VALUES (2, '角色管理', 'Lock', '/role', '@/views/role/index', 0, 2, 0);
INSERT INTO `menu` VALUES (3, '文章管理', 'Document', '/article', '@/views/article/index', 0, 3, 0);
INSERT INTO `menu` VALUES (4, '标签管理', 'PriceTag', '/tag', '@/views/tag/index', 0, 4, 0);
INSERT INTO `menu` VALUES (5, '分类管理', 'Collection', '/category', '@/views/category/index', 0, 5, 0);
INSERT INTO `menu` VALUES (6, '评论管理', 'ChatLineRound', '/comment', '@/views/comment/index', 0, 6, 0);
INSERT INTO `menu` VALUES (7, '菜单管理', 'Setting', '/menu', '@/views/menu/index', 0, 7, 0);
INSERT INTO `menu` VALUES (13, '个人中心', 'UserFilled', '/profile', '@/views/profile/index', 0, 8, 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '1', '超级管理员', '拥有至高无上的权力');
INSERT INTO `role` VALUES (2, '2', '管理员', '一人之下，万人之上');
INSERT INTO `role` VALUES (3, '3', '普通用户', '平民');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 'Spring Boot');
INSERT INTO `tag` VALUES (2, 'Spring Cloud');
INSERT INTO `tag` VALUES (3, 'Java');
INSERT INTO `tag` VALUES (4, 'Vue');
INSERT INTO `tag` VALUES (5, 'MySQL');
INSERT INTO `tag` VALUES (6, 'Redis');
INSERT INTO `tag` VALUES (7, 'Linux');
INSERT INTO `tag` VALUES (8, '博客');
INSERT INTO `tag` VALUES (9, '生活随笔');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 1-正常 0-禁用',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint NULL DEFAULT 1 COMMENT '性别 0-未知 1-男 2-女',
  `role_id` int NOT NULL DEFAULT 3 COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '1234', 1, 'https://diamond-blog.oss-cn-beijing.aliyuncs.com/defaultAvatar.jpg', '15399798037@163.com', 1, 2);
INSERT INTO `user` VALUES (2, 'Diamond', '1234', 1, 'https://diamond-blog.oss-cn-beijing.aliyuncs.com/defaultAvatar.jpg', '2210054954lh@gmail.com', 1, 1);
INSERT INTO `user` VALUES (3, 'wangye', '1234', 1, 'https://diamond-blog.oss-cn-beijing.aliyuncs.com/defaultAvatar.jpg', '15399798037@163.com', 1, 3);
INSERT INTO `user` VALUES (4, 'user', '1234', 1, 'https://diamond-blog.oss-cn-beijing.aliyuncs.com/defaultAvatar.jpg', '15399798037@163.com', 1, 3);

SET FOREIGN_KEY_CHECKS = 1;
