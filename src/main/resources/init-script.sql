CREATE TABLE IF NOT EXISTS `products` (
    `product_id` INT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS `nutrients` (
  `nutrients_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  `proteins` INT NOT NULL,
  `carbohydrates` INT NOT NULL,
  `fats` INT NOT NULL,
  `product_id` INT NOT NULL,
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
);