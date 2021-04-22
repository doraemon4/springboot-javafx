package com.stephen.learning.controller;

import com.google.common.collect.Lists;
import com.stephen.learning.model.User;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author jack
 */
@FXMLController
public class UserController implements Initializable {
    @FXML
    private TableView<User> userTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<User> users = Lists.newArrayList();
        users.add(User.builder().name("jack").sex("男").age(28).email("xy123zk@163.com").address("湖北武汉").build());
        users.add(User.builder().name("alice").sex("女").age(28).email("xy123zk@163.com").address("湖北武汉").build());
        users.add(User.builder().name("peter").sex("男").age(28).email("xy123zk@163.com").address("湖北武汉").build());
        users.add(User.builder().name("tom").sex("男").age(28).email("xy123zk@163.com").address("湖北武汉").build());
        users.add(User.builder().name("jenny").sex("男").age(28).email("xy123zk@163.com").address("湖北武汉").build());
        ObservableList<User> obsList = FXCollections.observableArrayList(users);
        userTable.setItems(obsList);
    }
}
