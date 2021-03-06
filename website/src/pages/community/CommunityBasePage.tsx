import * as React from "react";
import BaseRoutedPage from "../BaseRoutedPage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";

export default abstract class CommunityBasePage<S = {}> extends BaseRoutedPage<S> {

    active(): HeaderMenuType {
        return "community";
    }

}