import BasePage, {BasePageProps} from "../BasePage";
import {HeaderMenuType} from "../../components/layout/header/HeaderMenu";
import {RecordSetId} from "../../components/records/RecordSet";

export type RecordBasePageProps = BasePageProps;

export abstract class RecordBasePage<S> extends BasePage<S> {

    active(): HeaderMenuType {
        return "records";
    }

}