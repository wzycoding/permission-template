DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(20)  NOT NULL DEFAULT '' COMMENT '用户名',
  `telephone` varchar(13)  NOT NULL COMMENT '电话号码',
  `salt` varchar(20) NOT NULL COMMENT '盐值',
  `mail` varchar(50) NOT NULL COMMENT '电子邮箱',
  `password` varchar(40) NOT NULL COMMENT '加密后的密码',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户所在部门id',
  `remark` varchar(200)  DEFAULT '' COMMENT '备注',
  `operator` varchar(20)  NOT NULL DEFAULT '' COMMENT '操作者',
  `created_time` datetime NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_ip` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '最后一个更新者ip',
  `enable` int(11) NOT NULL DEFAULT '1' COMMENT '1为可用状态，0为不可用状态',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '0为未删除，1删除状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique_index` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级部门的id',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '部门层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '部门在当前层级下的顺序，由小到大',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `created_time` datetime NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip',
  `enable` int(11) NOT NULL DEFAULT '1' COMMENT '1为可用状态，0为不可用状态',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '0为未删除，1删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;