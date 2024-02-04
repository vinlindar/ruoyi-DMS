CREATE TABLE `dms_file_info` (
  `file_id` varchar(50) NOT NULL COMMENT '文件ID',
  `file_name` varchar(50) DEFAULT '' COMMENT '文件名',
  `file_path` varchar(255) DEFAULT '' COMMENT '文件路径',
  `author` varchar(30) DEFAULT '' COMMENT '作者',
  `reviewer` varchar(30) DEFAULT '无' COMMENT '审稿人',
  `file_type` int DEFAULT NULL COMMENT '文件类型',
  `file_size` varchar(50) DEFAULT '' COMMENT '文件大小',
  `file_status` int DEFAULT '0' COMMENT '文件状态',
  `belongteam` varchar(50) DEFAULT '' COMMENT '归属团队',
  `description` varchar(500) DEFAULT '无' COMMENT '文件描述',
  `update_by` varchar(30) DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='文件信息表';
