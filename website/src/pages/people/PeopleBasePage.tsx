import BasePage from "../BasePage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";

export abstract class PeopleBasePage<S = {}> extends BasePage<S> {

    active(): HeaderMenuType {
        return "people";
    }

}