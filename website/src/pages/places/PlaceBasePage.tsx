import BasePage from "../BasePage";
import {HeaderMenuType} from "../HeaderMenu";
import {RouteComponentProps} from "react-router";
import {PlaceId} from "../../components/places/Place";

export type PlacePageProps = RouteComponentProps<any>;

export abstract class PlaceBasePage<S> extends BasePage<PlacePageProps, S> {

    active(): HeaderMenuType {
        return "places";
    }

    readPlace(): PlaceId {
        const location = this.props.location;
        if (!location) return null;
        const search = new URLSearchParams(location.search);
        return search.get("place");
    }

}