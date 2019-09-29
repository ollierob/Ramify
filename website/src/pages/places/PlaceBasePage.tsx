import BasePage, {BasePageProps} from "../BasePage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";
import {PlaceId} from "../../components/places/Place";

export type PlaceBasePageProps = BasePageProps;

export abstract class PlaceBasePage<S> extends BasePage<S> {

    active(): HeaderMenuType {
        return "places";
    }

}