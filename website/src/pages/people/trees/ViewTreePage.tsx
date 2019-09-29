import {PeopleBasePage} from "../PeopleBasePage";

export class ViewTreePage extends PeopleBasePage {

    body() {
        return null;
    }

    private readTreeId() {
        return this.urlParameter("id");
    }

}