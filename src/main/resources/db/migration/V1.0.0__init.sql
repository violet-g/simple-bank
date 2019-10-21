CREATE TABLE account (
    id bigint AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    balance bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE transaction (
    id bigint AUTO_INCREMENT NOT NULL,
    from_account bigint NOT NULL,
    to_account bigint NOT NULL,
    amount bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (from_account) REFERENCES account(id),
    FOREIGN KEY (to_account) REFERENCES account(id)
);
