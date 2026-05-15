CREATE TABLE chats_categories (
    chat_id UUID NOT NULL,
    category_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_chat_categories PRIMARY KEY (chat_id, category_name),
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);