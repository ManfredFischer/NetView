class Translation {
    getTranslatedData() {
        var userLang = navigator.language || navigator.userLanguage;
        switch (userLang) {
            case 'de':
                return {
                    Default: {
                        searchPlaceholder : 'Suchen...',
                        search : 'Suchen'
                    },
                    Settings: {
                        Setting: {
                            title: 'Systemeinstellungen'
                        },
                        User: {
                            title: 'Systemeinstellungen'
                        }
                    },
                    Location: {
                        createTitle: 'Standord hinzufügen',
                        firmaPlaceholder : 'Firma...',
                        streetPlaceholder : 'Strasse...',
                        PLZPlaceholder : 'PLZ...',
                        placePlaceholder : 'Ort...',
                        descriptionPlaceholder : 'Beschreibung...'
                    },
                    Network: {
                        createTitle: 'Netzwerk hinzufügen'
                    },
                    Hardware: {
                        extended : 'Erweitert',
                        default : 'Allgemein',
                        comment : 'Kommentar',
                        protokoll : 'Protokoll',
                        createTitle: 'Hardware hinzufügen',
                        Data : {
                            Default : {
                                name : 'Namen',
                                user : 'Benutzer',
                                zugriffsId : 'Zugriffs-ID',
                                lastLogin : 'Letzte Anmeldung',
                                lastLogoff : 'Letzte Abmeldung',
                                InventurNr : 'Inventur-Nr.',
                                Tel : 'Telefon'
                            },
                            Extend : {
                                leasing : 'Leasing-Ende',
                                admin : 'Admin',
                                typ : 'Typ',
                                funktion : 'Funktion',
                                sn : 'Serien-Nr.',
                                kostenstelle : 'Kostenstelle'
                            }
                        }
                    },
                    Navigation: {
                        DefaultTitle: 'Info',
                        Menue: {
                            title: 'Menue',
                            import: 'Importieren',
                            export: 'Exportieren',
                            settings: 'Einstellungen',
                            newUser: 'User hinzufügen',
                            logout: 'Abmelden'
                        },
                        Network: {
                            title: 'Netzwerk Verwaltung',
                            search: 'Erweiterte Suche'
                        }
                    }

                }
                break;
            case 'en':
                return {
                    Default: {
                        searchPlaceholder : 'Search...',
                        search : 'Search'
                    },
                    Settings: {
                        Setting: {
                            title: 'Systemsettings'
                        },
                        User: {
                            title: 'User'
                        }
                    },
                    Location: {
                        createTitle: 'add Location',
                        firmaPlaceholder : 'Company...',
                        PLZPlaceholder : 'PLZ...',
                        streetPlaceholder : 'Street...',
                        placePlaceholder : 'Location...',
                        descriptionPlaceholder : 'Description...'
                    },
                    Network: {
                        createTitle: 'add Network'
                    },
                    Hardware: {
                        createTitle: 'add Hardware'
                    },
                    Navigation: {
                        DefaultTitle: 'Info',
                        Menue: {
                            title: 'Menue',
                            import: 'Import',
                            export: 'Export',
                            settings: 'Settings',
                            newUser: 'add User',
                            logout: 'Logout'
                        },
                        Network: {
                            title: 'Network Administration',
                            search: 'extended search'
                        }
                    }

                }
                break;

        }
        return {};
    }
}

export default new Translation().getTranslatedData();

