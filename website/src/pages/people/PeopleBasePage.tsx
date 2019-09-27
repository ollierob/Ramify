import BasePage from "../BasePage";
import {HeaderMenuType} from "../HeaderMenu";

export abstract class PeopleBasePage<S = {}> extends BasePage<{}, S> {

    active(): HeaderMenuType {
        return "people";
    }

}