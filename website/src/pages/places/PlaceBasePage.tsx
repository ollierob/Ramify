import BaseRoutedPage, {BaseRoutedPageProps} from "../BaseRoutedPage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";

export type PlaceBasePageProps = BaseRoutedPageProps;

export abstract class PlaceBasePage<S> extends BaseRoutedPage<S> {

    active(): HeaderMenuType {
        return "places";
    }

}