import * as React from "react";
import {DEFAULT_CHURCH_LOADER} from "../../../components/places/ChurchLoader";
import {PlacesPageProps} from "../PlacesPage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {InstitutionInfo} from "./InstitutionInfo";
import {Institution} from "../../../protobuf/generated/institution_pb";
import {PlaceMap} from "../../../components/places/PlaceMap";
import {Loading} from "../../../components/style/Loading";
import {InstitutionPage} from "./InstitutionPage";
import {PlaceId} from "../../../components/places/Place";

export default class SchoolPage extends InstitutionPage {

    loadInstitution(id: PlaceId) {
    }

}