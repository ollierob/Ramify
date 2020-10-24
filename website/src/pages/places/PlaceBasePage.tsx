import BaseRoutedPage, {BaseRoutedPageProps} from "../BaseRoutedPage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";
import {PlaceId} from "../../components/places/Place";

export type PlaceBasePageProps = BaseRoutedPageProps;

export abstract class PlaceBasePage<S> extends BaseRoutedPage<S> {

    active(): HeaderMenuType {
        return "places";
    }

}