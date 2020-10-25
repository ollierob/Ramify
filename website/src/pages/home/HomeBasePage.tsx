import BaseRoutedPage from "../BaseRoutedPage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";

export default abstract class HomeBasePage<S = {}> extends BaseRoutedPage<S> {

    active(): HeaderMenuType {
        return null;
    }

}