import BaseRoutedPage, {BaseRoutedPageProps} from "../BaseRoutedPage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";
import {RecordSetId} from "../../components/records/RecordSet";

export type RecordBasePageProps = BaseRoutedPageProps;

export abstract class RecordBasePage<S> extends BaseRoutedPage<S> {

    active(): HeaderMenuType {
        return "records";
    }

}