/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fahrzeug_vermietung;

/**
 *
 * @author oskar
 */
public enum CarBrands {
    BMW,VW,Mercedes,Fiat,Tesla,Audi,Ford;
    
    
    
    public CarBrands whichBrand(String name){
        switch(name){
            case "BWM":
                return CarBrands.BMW;
            case "VW":
                return CarBrands.VW;
            case "Mercedes":
                return CarBrands.Mercedes;
            case "Fiat":
                return CarBrands.Fiat;
            case "Tesla":
                return CarBrands.Tesla;
            case "Audi":
                return CarBrands.Audi;
            case "Ford":
                return CarBrands.Ford;
        }
        return null;
    }
}
