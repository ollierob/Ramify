import * as React from "react";
import {DEFAULT_CHURCH_LOADER} from "../../../components/places/ChurchLoader";
import {asyncLoadData} from "../../../components/fetch/AsyncData";
import {InstitutionPage} from "./InstitutionPage";
import {PlaceId} from "../../../components/places/Place";

export default class ChurchPage extends InstitutionPage {

    private readonly churchLoader = DEFAULT_CHURCH_LOADER;

    loadInstitution(id: PlaceId) {
        if (!id) return;
        asyncLoadData(id, this.churchLoader.loadChurch, church => this.setState({institution: church}));
    }

}