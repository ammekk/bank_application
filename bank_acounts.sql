CREATE TABLE `bank_accounts` (
 `username` varchar(21) NOT NULL,
 `account_num` int(11) NOT NULL,
 `balance` varchar(50) NOT NULL,
 `date_created` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8