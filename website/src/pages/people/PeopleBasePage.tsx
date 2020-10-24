import BaseRoutedPage from "../BaseRoutedPage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";

export abstract class PeopleBasePage<S = {}> extends BaseRoutedPage<S> {

    active(): HeaderMenuType {
        return "people";
    }

}